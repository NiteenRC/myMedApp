package com.nc.med.service;

import com.nc.med.Beans.ProductBean;
import com.nc.med.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> saveProducts(List<Product> products);

    Product saveProduct(Product product);

    Product findByProductName(String productName);

    Product findByProductID(Integer productID);

    void deleteProduct(Integer productID);

    List<ProductBean> fetchAllProducts(List<Product> products);

    List<ProductBean> advancedSearch(Integer categoryID, String productName);

}
