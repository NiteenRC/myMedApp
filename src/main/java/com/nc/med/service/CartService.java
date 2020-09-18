package com.nc.med.service;

import java.text.ParseException;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.nc.med.model.Cart;

public interface CartService {
	Cart saveCart(Cart cart);

	Cart findByCartID(Integer cartID);

	void deleteCart(Cart cartID);

	Cart findCartByProductName(String productName);

	Cart addToCart(List<Cart> carts);

	ResponseEntity<?> removeFromCart(List<Cart> carts);

	List<Cart> findAllCarts();

	String writeCartListToExcel(List<Cart> carts);

	List<Cart> findByDates(String startDate, String endDate) throws ParseException;
}
