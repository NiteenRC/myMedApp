package com.nc.med.service;

import com.nc.med.Beans.ProductBean;
import com.nc.med.exception.CustomErrorType;
import com.nc.med.model.Cart;
import com.nc.med.model.Product;
import com.nc.med.repo.CartRepo;
import com.nc.med.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepo productRepo;

    @Autowired
    CartRepo cartRepo;

    @Override
    public List<ProductBean> advancedSearch(Integer categoryID, String productName) {
        List<Product> products = null;
        if (categoryID > 0 && !productName.equals("null")) {
            products = productRepo.findByCategoryCategoryIDAndProductNameContainingIgnoreCase(categoryID,
                    productName);
        } else if (categoryID > 0 && productName.equals("null")) {
            products = productRepo.findByCategoryCategoryID(categoryID);
        } else if (categoryID == 0 && !productName.equals("null")) {
            products = productRepo.findByProductNameContainingIgnoreCase(productName);
        } else {
            products = productRepo.findAll();
        }
        return fetchAllProducts(products);
    }

    @Override
    public List<ProductBean> fetchAllProducts(List<Product> products) {
        List<Product> productList = null;
        if (products.isEmpty()) {
            productList = productRepo.findAll();
        } else {
            productList = products;
        }

        List<ProductBean> productBeans = new ArrayList<>();
        ProductBean productBean = null;
        for (Product product : productList) {
            productBean = new ProductBean();
            productBean.setProductName(product.getProductName());
            productBean.setPrice(product.getPrice());
            productBean.setProductDesc(product.getProductDesc());
            //Cart cart = cartRepo.findByProductID(product.getProductID());
           // if (cart != null) {
            productBean.setQty(product.getQty());
          //  } else {
          //      productBean.setQty(0);
          //  }
            productBean.setProductID(product.getProductID());
            productBean.setCategoryName(product.getCategory().getCategoryName());
            productBeans.add(productBean);
        }
        return productBeans;
    }

    @Override
    public void deleteProduct(Integer productID) {
        productRepo.delete(productID);
    }

    @Override
    public Product findByProductID(Integer productID) {
        return productRepo.findOne(productID);
    }

    @Override
    public Product findByProductName(String productName) {
        return productRepo.findByProductName(productName);
    }

    @Override
    public List<Product> saveProducts(List<Product> products) {
        return productRepo.save(products);
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepo.save(product);
    }

    @Override
    public Product addToStock(List<Product> products) {
        for (Product product : products) {
            Product product2 = productRepo.findByProductName(product.getProductName());
            if (product2 == null) {
                productRepo.save(product);
            } else {
            	product2.setQty(product2.getQty() + product.getQty());
                productRepo.save(product2);
            }
        }
        return null;
    }
    
	@Override
	public ResponseEntity removeFromStock(List<Product> products) {
        boolean validation = true;
        for (Product product : products) {
        	Product product2 = productRepo.findByProductName(product.getProductName());
            if (product.getProductName() != null) {
                if (product2 == null) {
                    validation = true;
                    return new ResponseEntity(new CustomErrorType("Stock is not avaible for " + product.getProductName()), HttpStatus.NOT_FOUND);
                } else {
                    if (product2.getQty() < product.getQty()) {
                        validation = true;
                        return new ResponseEntity(new CustomErrorType("Stock avaible for " + product.getProductName() + " is " + product2.getQty()), HttpStatus.NOT_FOUND);
                    }
                }
            }
        }

        if (validation) {
            for (Product product : products) {
                if (product.getProductName() != null) {
                	Product product2 = productRepo.findByProductName(product.getProductName());
                    product2.setQty(product2.getQty() - product.getQty());
                    productRepo.save(product2);
                }
            }
        }
        return null;
    }
}
