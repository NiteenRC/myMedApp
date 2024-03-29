package com.nc.med.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.nc.med.Beans.ProductBean;
import com.nc.med.model.Product;

public interface ProductService {

	List<Product> saveProducts(List<Product> products);

	Product saveProduct(Product product);

	Product findByProductName(String productName);

	Product findByProductID(Integer productID);

	void deleteProduct(Product productID);

	List<ProductBean> fetchAllProducts(List<Product> products);

	List<ProductBean> advancedSearch(Integer categoryID, String productName);

	ResponseEntity<?> removeFromStock(List<Product> products);

	Product addToStock(List<Product> products);

	List<Product> findAllProduct();
	
	ResponseEntity<?> removeProductTemp(List<Product> products);

	List<Product> removeProductGetTemp();

}
