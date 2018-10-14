package com.fico.demo.controller;

import com.fico.demo.exception.CustomErrorType;
import com.fico.demo.model.Cart;
import com.fico.demo.model.Category;
import com.fico.demo.model.Product;
import com.fico.demo.repo.CartRepo;
import com.fico.demo.repo.CategoryRepo;
import com.fico.demo.repo.ProductRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.fico.demo.util.WebUrl.*;

@RestController
public class ProductController {

    private final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    public ProductRepo productRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    public CartRepo cartRepo;

    @RequestMapping(value = PRODUCTS, method = RequestMethod.POST)
    public ResponseEntity<List<Product>> addproduct(@RequestBody List<Product> productList) {
        if (productList == null) {
            return new ResponseEntity(new CustomErrorType("input is empty"), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(productRepo.save(productList), HttpStatus.CREATED);
    }

    @RequestMapping(value = PRODUCT_AND_CATEGORYID, method = RequestMethod.POST)
    public ResponseEntity<Product> addproductList(@PathVariable int categoryID, @RequestBody Product product) {
        Category category = categoryRepo.findOne(categoryID);
        product.setCategory(category);
        if (product == null) {
            return new ResponseEntity(new CustomErrorType("Product is not saved"), HttpStatus.NOT_FOUND);
        }

        Product productName = productRepo.findByProductName(product.getProductName());
        if (productName != null) {
            return new ResponseEntity(new CustomErrorType("Product name already exist!!"), HttpStatus.NOT_FOUND);
        }
        productRepo.save(product);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @RequestMapping(value = PRODUCT_BY_PRODUCTID, method = RequestMethod.DELETE)
    public ResponseEntity<Product> deleteProduct(@PathVariable Integer productID) {
        Product product = productRepo.findOne(productID);
        if (product == null) {
            return new ResponseEntity(new CustomErrorType("ProductID: " + productID + " not found."), HttpStatus.NOT_FOUND);
        }
        productRepo.delete(product);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = PRODUCT, method = RequestMethod.GET)
    public ResponseEntity<List<Product>> productList() {
        return new ResponseEntity<>(productRepo.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = PRODUCT_BY_PRODUCTID, method = RequestMethod.GET)
    public ResponseEntity<Product> getProductsById(@PathVariable int productID) {
        Product product = productRepo.findOne(productID);
        if (product == null) {
            return new ResponseEntity(new CustomErrorType("ProductID: " + productID + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @RequestMapping(value = ADVANCED_SEARCH, method = RequestMethod.GET)
    public ResponseEntity<List<Product>> getProductsByCategoryId(@PathVariable Integer categoryID, @PathVariable String productName) {
        List<Product> product = null;
        if (categoryID > 0 && !productName.equals("null")) {
            product = productRepo.findByCategoryCategoryIDAndProductNameContainingIgnoreCase(categoryID,
                    productName);
        } else if (categoryID > 0 && productName.equals("null")) {
            product = productRepo.findByCategoryCategoryID(categoryID);
        } else if (categoryID == 0 && !productName.equals("null")) {
            product = productRepo.findByProductNameContainingIgnoreCase(productName);
        } else {
            product = productRepo.findAll();
        }

        List<Product> products = new ArrayList<>();

        for(Product p : product){
            Cart cart = cartRepo.findByProductID(p.getProductID());
            if(cart != null){
                p.setQty(cart.getQty());
            } else {
                p.setQty(0);
            }
            products.add(p);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @RequestMapping(value = PRODUCTS, method = RequestMethod.GET)
    public ResponseEntity<List<Product>> getProductsForAllCategories() {
        List<Product> products = new ArrayList<>();
        List<Product> productList = productRepo.findAll();
        for(Product p : productList){
            Cart cart = cartRepo.findByProductID(p.getProductID());
            if(cart != null){
                p.setQty(cart.getQty());
            } else {
                p.setQty(0);
            }
            products.add(p);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}