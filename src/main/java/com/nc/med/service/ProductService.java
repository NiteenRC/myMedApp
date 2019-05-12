package com.nc.med.service;

import com.nc.med.Beans.ProductBean;
import com.nc.med.model.Cart;
import com.nc.med.model.Product;

import java.util.List;

import org.springframework.http.ResponseEntity;

public interface ProductService {

    List<Product> saveProducts(List<Product> products);

    Product saveProduct(Product product);

    Product findByProductName(String productName);

    Product findByProductID(Integer productID);

    void deleteProduct(Integer productID);

    List<ProductBean> fetchAllProducts(List<Product> products);

    List<ProductBean> advancedSearch(Integer categoryID, String productName);

	ResponseEntity removeFromStock(List<Product> products);

	Product addToStock(List<Product> products);

}
