package com.nc.med.service;

import com.nc.med.model.Cart;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CartService {
    Cart saveCart(Cart cart);

    Cart findByCartID(Integer cartID);

    void deleteCart(Integer cartID);

    Cart findCartByProductName(String productName);

    Cart addToCart(List<Cart> carts);

    ResponseEntity removeFromCart(List<Cart> carts);
}
