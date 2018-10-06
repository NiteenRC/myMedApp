package com.fico.demo.controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fico.demo.exception.CustomErrorType;
import com.fico.demo.model.Category;
import com.fico.demo.model.Product;
import com.fico.demo.repo.CategoryRepo;
import com.fico.demo.repo.ProductRepo;
import com.fico.demo.util.Utility;

import static com.fico.demo.util.WebUrl.PRODUCT_BY_CATEGORYID_SORTTYPE;
import static com.fico.demo.util.WebUrl.PRODUCTS;
import static com.fico.demo.util.WebUrl.PRODUCT;
import static com.fico.demo.util.WebUrl.PRODUCT_BY_PRODUCTID;
import static com.fico.demo.util.WebUrl.PRODUCT_AND_CATEGORYID;
import static com.fico.demo.util.WebUrl.PRODUCT_BY_NAME_CATEGORYID_PRODUCTNAME_SORTTYPE;

@RestController
public class ProductController {

    private final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    public ProductRepo productRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @RequestMapping(value = PRODUCTS, method = RequestMethod.POST)
    public ResponseEntity<List<Product>> addproduct(@RequestBody List<Product> productList) {
        LOGGER.info("addproduct ");
        LOGGER.debug("aaa ");
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
        productRepo.save(product);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @RequestMapping(value = PRODUCT_BY_PRODUCTID, method = RequestMethod.DELETE)
    public ResponseEntity<Product> deleteProduct(@PathVariable int productID) {
        Product product = productRepo.findOne(productID);
        if (product != null) {
            productRepo.delete(productID);
            return new ResponseEntity<>(product, HttpStatus.OK);
        }
        return new ResponseEntity(new CustomErrorType("ProductID: " + productID + " not found."), HttpStatus.NOT_FOUND);
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

    @RequestMapping(value = PRODUCT_BY_NAME_CATEGORYID_PRODUCTNAME_SORTTYPE, method = RequestMethod.GET)
    public ResponseEntity<List<Product>> getProductsByCategoryIDAndProductName(@PathVariable Integer categoryID, @PathVariable String productName) {
        List<Product> product;
        if (categoryID == 0) {
            product = productRepo.findByProductNameContainingIgnoreCase(productName);
        } else if (productName == null) {
            product = productRepo.findAll();
        } else {
            product = productRepo.findByCategoryCategoryIDAndProductNameContainingIgnoreCaseOrderByPriceDesc(categoryID,
                    productName);
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @RequestMapping(value = PRODUCT_BY_CATEGORYID_SORTTYPE, method = RequestMethod.GET)
    public ResponseEntity<List<Product>> getProductsByCategoryId(@PathVariable Integer categoryID) {
        List<Product> productList = productRepo.findByCategoryCategoryIDOrderByPriceDesc(categoryID);
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @RequestMapping(value = PRODUCTS, method = RequestMethod.GET)
    public ResponseEntity<List<Product>> getProductsForAllCategories() {
        return new ResponseEntity<>(productRepo.findAll(), HttpStatus.OK);
    }
}