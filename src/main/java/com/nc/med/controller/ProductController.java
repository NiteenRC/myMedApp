package com.nc.med.controller;

import com.nc.med.Beans.ProductBean;
import com.nc.med.exception.CustomErrorType;
import com.nc.med.model.Category;
import com.nc.med.model.Product;
import com.nc.med.service.CategoryService;
import com.nc.med.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

import static com.nc.med.util.WebUrl.*;

@RestController
public class ProductController {
    private final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = PRODUCTS, method = RequestMethod.POST)
    public ResponseEntity<List<Product>> saveProducts(@RequestBody List<Product> productList) {
        if (productList == null) {
            return new ResponseEntity(new CustomErrorType("input is empty"), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(productService.saveProducts(productList), HttpStatus.CREATED);
    }

    @RequestMapping(value = PRODUCT_AND_CATEGORYID, method = RequestMethod.POST)
    public ResponseEntity<Product> saveProduct(@PathVariable int categoryID, @RequestBody Product product) {
        Category category = categoryService.findByCategoryID(categoryID);
        product.setCategory(category);
        if (product == null) {
            return new ResponseEntity(new CustomErrorType("Product is not saved"), HttpStatus.NOT_FOUND);
        }

        Product productName = productService.findByProductName(product.getProductName());
        if (productName != null) {
            return new ResponseEntity(new CustomErrorType("Product name already exist!!"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productService.saveProduct(product), HttpStatus.CREATED);
    }

    @RequestMapping(value = PRODUCT_BY_PRODUCTID, method = RequestMethod.DELETE)
    public ResponseEntity<Product> deleteProduct(@PathVariable Integer productID) {
        Product product = productService.findByProductID(productID);
        if (product == null) {
            return new ResponseEntity(new CustomErrorType("ProductID: " + productID + " not found."), HttpStatus.NOT_FOUND);
        }
        productService.deleteProduct(productID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = PRODUCT_BY_PRODUCTID, method = RequestMethod.GET)
    public ResponseEntity<Product> getProductsById(@PathVariable int productID) {
        Product product = productService.findByProductID(productID);
        if (product == null) {
            return new ResponseEntity(new CustomErrorType("ProductID: " + productID + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @RequestMapping(value = ADVANCED_SEARCH, method = RequestMethod.GET)
    public ResponseEntity<List<ProductBean>> getProductsByCategoryId(@PathVariable Integer categoryID, @PathVariable String productName) {
        return new ResponseEntity<>(productService.advancedSearch(categoryID, productName), HttpStatus.OK);
    }

    @RequestMapping(value = PRODUCTS, method = RequestMethod.GET)
    public ResponseEntity<List<ProductBean>> getProductsForAllCategories() {
        return new ResponseEntity<>(productService.fetchAllProducts(Collections.emptyList()), HttpStatus.OK);
    }
}