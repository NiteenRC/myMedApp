package com.nc.med.controller;

import com.nc.med.exception.CustomErrorType;
import com.nc.med.model.Cart;
import com.nc.med.service.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.nc.med.util.WebUrl.*;

@RestController
public class CartController {

    public static final Logger LOGGER = LoggerFactory.getLogger(CartController.class);

    @Autowired
    public CartService cartService;

    @RequestMapping(value = CARTS_ADD, method = RequestMethod.POST)
    public ResponseEntity addCartList(@RequestBody List<Cart> carts) {
        return new ResponseEntity<>(cartService.addToCart(carts), HttpStatus.OK);
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