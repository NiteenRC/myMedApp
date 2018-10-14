package com.fico.demo.controller;

import com.fico.demo.exception.CustomErrorType;
import com.fico.demo.model.Cart;
import com.fico.demo.repo.CartRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.fico.demo.util.WebUrl.*;

@RestController
public class CartController {

    public static final Logger LOGGER = LoggerFactory.getLogger(CartController.class);

    @Autowired
    public CartRepo cartRepo;

    @RequestMapping(value = CARTS, method = RequestMethod.POST)
    public ResponseEntity addCartList(@RequestBody List<Cart> carts) {
        List<Cart> cartList = cartRepo.findAll();
        for (Cart cart : cartList) {
            for (Cart carts2 : carts) {
                if (cart.getProductID() == carts2.getProductID()) {
                    cart.setCartID(cart.getCartID());
                    cart.setQty(cart.getQty() + carts2.getQty());
                    cartRepo.save(cart);
                }
            }
        }

        if (cartList.isEmpty()) {
            return new ResponseEntity<>(cartRepo.save(carts), HttpStatus.CREATED);
        }
        return null;
    }

    @RequestMapping(value = CARTS_REMOVE, method = RequestMethod.POST)
    public ResponseEntity<Cart> removeCartList(@RequestBody List<Cart> carts) {
        List<Cart> cartList = cartRepo.findAll();
        for (Cart cart : cartList) {
            for (Cart carts2 : carts) {
                if (cart.getProductID() == carts2.getProductID()) {
                    cart.setCartID(cart.getCartID());
                    if (cart.getQty() < carts2.getQty()) {
                        return new ResponseEntity(new CustomErrorType("Stock is not avaible for " + carts2.getProductName()), HttpStatus.NOT_FOUND);
                    }
                    cart.setQty(cart.getQty() - carts2.getQty());
                    cartRepo.save(cart);
                }
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
}