package com.nc.med.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nc.med.controller.CartController;
import com.nc.med.exception.CustomErrorType;
import com.nc.med.model.Cart;
import com.nc.med.repo.CartRepo;

@Service
public class CartServiceImpl implements CartService {
	public static final Logger LOGGER = LoggerFactory.getLogger(CartController.class);
	private static final String FILE_PATH = System.getProperty("user.home") + "/Downloads/testWriteStudents.xlsx";

	@Autowired
	private CartRepo cartRepo;

	@Override
	public Cart saveCart(Cart cart) {
		return cartRepo.save(cart);
	}

	@Override
	public Cart findCartByProductName(String productName) {
		return cartRepo.findByProductName(productName);
	}

	@Override
	public Cart findByCartID(Integer cartID) {
		return cartRepo.findOne(cartID);
	}

	@Override
	public void deleteCart(Integer cartID) {
		cartRepo.delete(cartID);
	}

	@Override
	public List<Cart> findAllCarts() {
		return cartRepo.findAll();
	}

	@Override
	public ResponseEntity<?> removeFromCart(List<Cart> carts) {
		boolean validation = true;
		for (Cart cart : carts) {
			Cart cart2 = cartRepo.findByProductName(cart.getProductName());
			if (cart.getProductName() != null) {
				if (cart2 == null) {
					validation = true;
					return new ResponseEntity<>(
							new CustomErrorType("Stock is not avaible for " + cart.getProductName()),
							HttpStatus.NOT_FOUND);
				} else {
					if (cart2.getQty() < cart.getQty()) {
						validation = true;
						return new ResponseEntity<>(
								new CustomErrorType(
										"Stock avaible for " + cart2.getProductName() + " is " + cart2.getQty()),
								HttpStatus.NOT_FOUND);
					}
				}
			}
		}

		if (validation) {
			for (Cart car : carts) {
				if (car.getProductName() != null) {
					Cart cart2 = cartRepo.findByProductName(car.getProductName());
					cart2.setQty(cart2.getQty() - car.getQty());
					cartRepo.save(cart2);
				}
			}
		}
		return null;
	}

	@Override
	public String writeCartListToExcel(List<Cart> carts) {
		final Workbook workbook = new XSSFWorkbook();
		Sheet cartSheet = workbook.createSheet("Cart");
		int rowIndex = 1, totalQty = 0, totalPrice = 0, totalAmount = 0, lastIndex = 0;

		Row header = cartSheet.createRow(0);
		header.createCell(0).setCellValue(("Date"));
		header.createCell(1).setCellValue(("Product name"));
		header.createCell(2).setCellValue(("Price"));
		header.createCell(3).setCellValue(("Qty"));
		header.createCell(4).setCellValue(("Total Amount"));

		for (Cart cart : carts) {
			int cellIndex = 0;
			Row row = cartSheet.createRow(rowIndex++);
			// LOGGER.info("SSSS {}",cart.getDate());
			row.createCell(cellIndex++).setCellValue(("" + cart.getDate()).substring(0, 10));
			row.createCell(cellIndex++).setCellValue(cart.getProductName());
			row.createCell(cellIndex++).setCellValue(cart.getPrice());
			row.createCell(cellIndex++).setCellValue(cart.getQty());
			row.createCell(cellIndex++).setCellValue(cart.getPrice() * cart.getQty());

			lastIndex = rowIndex;
			totalQty += cart.getQty();
			totalPrice += cart.getPrice();
			totalAmount += cart.getPrice() * cart.getQty();
		}

		Row summary = cartSheet.createRow(lastIndex + 1);
		summary.createCell(2).setCellValue(totalPrice);
		summary.createCell(3).setCellValue(totalQty);
		summary.createCell(4).setCellValue(totalAmount);

		for (short i = cartSheet.getRow(0).getFirstCellNum(), end = cartSheet.getRow(0)
				.getLastCellNum(); i < end; i++) {
			cartSheet.autoSizeColumn(i);
		}
		try {
			FileOutputStream fos = new FileOutputStream(FILE_PATH);
			workbook.write(fos);
			fos.close();
			LOGGER.info(FILE_PATH + " is successfully written");
		} catch (FileNotFoundException e) {
			LOGGER.error("File not found ", e);
		} catch (IOException e) {
			LOGGER.error("Exception while creating excel ", e);
		} finally {
			if (workbook != null) {
				try {
					workbook.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return FILE_PATH;
	}

	@Override
	public Cart addToCart(List<Cart> carts) {
		for (Cart cart : carts) {
			Cart cart2 = cartRepo.findByProductName(cart.getProductName());
			if (cart2 == null) {
				cartRepo.save(cart);
			} else {
				cart2.setQty(cart2.getQty() + cart.getQty());
				cartRepo.save(cart2);
			}
		}
		return null;
	}

	@Override
	public List<Cart> findByDates(String startDate, String endDate) throws ParseException {
		List<Cart> carts = cartRepo.findAll();
		Date fromDate = new SimpleDateFormat("dd-MM-yyyy").parse(startDate);
		Date toDate = new SimpleDateFormat("dd-MM-yyyy").parse(endDate);

		return carts.stream().filter(
				cart -> cart.getDate().getTime() >= fromDate.getTime() && cart.getDate().getTime() <= toDate.getTime())
				.collect(Collectors.toList());
	}
}
