package com.fico.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fico.demo.exception.CustomErrorType;
import com.fico.demo.model.Cart;
import com.fico.demo.repo.CartRepo;

import static com.fico.demo.util.WebUrl.CART_BY_CARTID;
import static com.fico.demo.util.WebUrl.CARTS;
import static com.fico.demo.util.WebUrl.CARTS_REMOVE;
import static com.fico.demo.util.WebUrl.CART;
import static com.fico.demo.util.WebUrl.CART_BY_USERID;

@RestController
public class CartController {

    public static final Logger LOGGER = LoggerFactory.getLogger(CartController.class);

    @Autowired
    public CartRepo cartRepo;

    @RequestMapping(value = CARTS, method = RequestMethod.POST)
    public ResponseEntity addCartList(@RequestBody List<Cart> carts) {
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

    @RequestMapping(value = CARTS_REMOVE, method = RequestMethod.POST)
    public ResponseEntity removeCartList(@RequestBody List<Cart> carts) {
        boolean validation = true;
        for (Cart cart : carts) {
            Cart cart2 = cartRepo.findByProductName(cart.getProductName());
            if (cart2 == null) {
                validation = true;
                return new ResponseEntity(new CustomErrorType("Stock is not avaible for " + cart.getProductName()), HttpStatus.NOT_FOUND);
            } else {
                if (cart2.getQty() < cart.getQty()) {
                    validation = true;
                    return new ResponseEntity(new CustomErrorType("Stock avaible for " + cart2.getProductName() + " is " + cart2.getQty()), HttpStatus.NOT_FOUND);
                }
            }
        }

        if (validation) {
            for (Cart car : carts) {
                Cart cart2 = cartRepo.findByProductName(car.getProductName());
                cart2.setQty(cart2.getQty() - car.getQty());
                cartRepo.save(cart2);
            }
        }
        return null;
    }

    @RequestMapping(value = CART_BY_CARTID, method = RequestMethod.DELETE)
    public ResponseEntity<Cart> deleteCart(@PathVariable int cartID) {
        Cart cart = cartRepo.findOne(cartID);
        if (cart == null) {
            return new ResponseEntity(new CustomErrorType("cartID: " + cartID + " not found."), HttpStatus.NOT_FOUND);
        }
        cartRepo.delete(cartID);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @RequestMapping(value = CART_BY_USERID, method = RequestMethod.GET)
    public ResponseEntity<List<Cart>> fetchCartList(@PathVariable int userID) {
        return new ResponseEntity<>(cartRepo.findAllCartsByUserID(userID), HttpStatus.OK);
    }
}