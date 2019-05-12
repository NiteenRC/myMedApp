package com.nc.med.service;

import com.nc.med.exception.CustomErrorType;
import com.nc.med.model.Cart;
import com.nc.med.repo.CartRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    CartRepo cartRepo;

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
    public ResponseEntity removeFromCart(List<Cart> carts) {
        boolean validation = true;
        for (Cart cart : carts) {
            Cart cart2 = cartRepo.findByProductName(cart.getProductName());
            if (cart.getProductName() != null) {
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
}
