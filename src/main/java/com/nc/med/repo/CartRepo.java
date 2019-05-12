package com.nc.med.repo;

import com.nc.med.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepo extends JpaRepository<Cart, Integer> {

	Cart findByProductName(String productName);

	Cart findByProductID(Integer productID);

}
