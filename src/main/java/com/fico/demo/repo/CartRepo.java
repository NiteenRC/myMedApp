package com.fico.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fico.demo.model.Cart;

public interface CartRepo extends JpaRepository<Cart, Integer> {

	Cart findByProductName(String productName);

	List<Cart> findAllCartsByUserID(int userID);

	Cart findByProductID(Integer productID);
}
