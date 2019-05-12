package com.nc.med.controller;

import static com.nc.med.util.WebUrl.CARTS_ADD;
import static com.nc.med.util.WebUrl.CARTS_REMOVE;
import static com.nc.med.util.WebUrl.CARTS_REPORT;
import static com.nc.med.util.WebUrl.CART_BY_CARTID;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nc.med.exception.CustomErrorType;
import com.nc.med.model.Cart;
import com.nc.med.service.CartService;
import com.nc.med.service.ProductService;

@RestController
public class CartController {

	public static final Logger LOGGER = LoggerFactory.getLogger(CartController.class);

	@Autowired
	public CartService cartService;

	@Autowired
	ProductService productService;

	static String downloadDirectory = System.getProperty("user.home") + "/Downloads";
	private static final String FILE_PATH = downloadDirectory + "/testWriteStudents.xlsx";

	@RequestMapping(value = CARTS_ADD, method = RequestMethod.POST)
	public ResponseEntity addCartList(@RequestBody List<Cart> carts) {
		return new ResponseEntity<>(cartService.addToCart(carts), HttpStatus.OK);
	}

	@GetMapping(CARTS_REPORT)
	public ResponseEntity download(@PathVariable String fromDate, @PathVariable String toDate) throws IOException {
		List<Cart> persons = cartService.findAllCarts();
		//persons.stream().filter(x-> (fromDate < x.getDate() && toDate > x.getDate())).collect(Collectors.toList());
		
		Workbook workbook = writeStudentsListToExcel(persons);
		// List<String> persons=null;
		// .write(Paths.get(System.getProperty("java.io.tmpdir")+"/outputList.txt"),
		// persons);
		File file = new File(FILE_PATH); // a method that returns file for given ID
		if (!file.exists()) { // handle FNF
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}

		try {
			// Generate Excel from DTO using any logic after that do the following
			byte[] body = Files.readAllBytes(Paths.get(FILE_PATH));
			HttpHeaders header = new HttpHeaders();
			header.setContentType(new MediaType("application", "xlsx"));
			header.set("Content-Disposition", "inline; filename=" + "h");
			header.setCacheControl("must-revalidate, post-check=0, pre-check=0");
			header.setContentLength(body.length);

			return new ResponseEntity<byte[]>(body, header, HttpStatus.OK);
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	public static Workbook writeStudentsListToExcel(List<Cart> studentList) {
		Workbook workbook = new XSSFWorkbook();
		Sheet studentsSheet = workbook.createSheet("Students");
		int rowIndex = 1;

		Row row1 = studentsSheet.createRow(0);
		row1.createCell(0).setCellValue(("Date"));
		row1.createCell(1).setCellValue(("Product name"));
		row1.createCell(2).setCellValue(("Price"));
		row1.createCell(3).setCellValue(("Qty"));
		row1.createCell(4).setCellValue(("Total Amount"));

		int totalQty = 0;
		int totalPrice = 0;
		int totalAmount = 0;
		int lastIndex = 0;

		for (Cart student : studentList) {
			int cellIndex = 0;
			Row row = studentsSheet.createRow(rowIndex++);

			row.createCell(cellIndex++).setCellValue(("" + student.getDate()).substring(0, 10));
			row.createCell(cellIndex++).setCellValue(student.getProductName());
			row.createCell(cellIndex++).setCellValue(student.getPrice());
			row.createCell(cellIndex++).setCellValue(student.getQty());
			row.createCell(cellIndex++).setCellValue(student.getPrice() * student.getQty());

			lastIndex = rowIndex;
			totalQty += student.getQty();
			totalPrice += student.getPrice();
			totalAmount += student.getPrice() * student.getQty();
		}
		
		Row row3 = studentsSheet.createRow(lastIndex+1);
		row3.createCell(2).setCellValue(totalPrice);
		row3.createCell(3).setCellValue(totalQty);
		row3.createCell(4).setCellValue(totalAmount);
		
		for (short i = studentsSheet.getRow(0).getFirstCellNum(), end = studentsSheet.getRow(0)
				.getLastCellNum(); i < end; i++) {
			studentsSheet.autoSizeColumn(i);
		}

		// write this workbook in excel file.
		try {
			FileOutputStream fos = new FileOutputStream(FILE_PATH);
			workbook.write(fos);
			fos.close();
			System.out.println(FILE_PATH + " is successfully written");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return workbook;
	}

	@RequestMapping(value = CARTS_REMOVE, method = RequestMethod.POST)
	public ResponseEntity removeCartList(@RequestBody List<Cart> carts) {
		return cartService.removeFromCart(carts);
	}

	@RequestMapping(value = CART_BY_CARTID, method = RequestMethod.DELETE)
	public ResponseEntity<Cart> deleteCart(@PathVariable int cartID) {
		Cart cart = cartService.findByCartID(cartID);
		if (cart == null) {
			return new ResponseEntity(new CustomErrorType("cartID: " + cartID + " not found."), HttpStatus.NOT_FOUND);
		}
		cartService.deleteCart(cartID);
		return new ResponseEntity<>(cart, HttpStatus.OK);
	}
}