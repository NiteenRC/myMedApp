package com.nc.med.controller;

import static com.nc.med.util.WebUrl.CARTS_ADD;
import static com.nc.med.util.WebUrl.CARTS_REMOVE;
import static com.nc.med.util.WebUrl.CARTS_REPORT;
import static com.nc.med.util.WebUrl.CART_BY_CARTID;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
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

	@PostMapping(CARTS_ADD)
	public ResponseEntity<?> addCartList(@RequestBody List<Cart> carts) {
		return new ResponseEntity<>(cartService.addToCart(carts), HttpStatus.OK);
	}

	@GetMapping(CARTS_REPORT)
	public ResponseEntity<?> download(@PathVariable String fromDate, @PathVariable String toDate) throws IOException {
		List<Cart> carts = cartService.findAllCarts();
		String filePath = cartService.writeCartListToExcel(carts);
		File file = new File(filePath);
		if (!file.exists()) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}

		try {
			byte[] body = Files.readAllBytes(Paths.get(filePath));
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

	@PostMapping(CARTS_REMOVE)
	public ResponseEntity<?> removeCartList(@RequestBody List<Cart> carts) {
		return cartService.removeFromCart(carts);
	}

	@DeleteMapping(CART_BY_CARTID)
	public ResponseEntity<?> deleteCart(@PathVariable int cartID) {
		Cart cart = cartService.findByCartID(cartID);
		if (cart == null) {
			return new ResponseEntity<>(new CustomErrorType("cartID: " + cartID + " not found."), HttpStatus.NOT_FOUND);
		}
		cartService.deleteCart(cartID);
		return new ResponseEntity<>(cart, HttpStatus.OK);
	}
}