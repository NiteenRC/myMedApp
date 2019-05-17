package com.nc.med.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nc.med.model.Cart;

public interface CartRepo extends JpaRepository<Cart, Integer> {

	Cart findByProductName(String productName);

	Cart findByProductID(Integer productID);
	
	//List<Cart> findAllByDateBetween(Date fromDate, Date toDate);

}
