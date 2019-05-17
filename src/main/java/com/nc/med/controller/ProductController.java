package com.nc.med.controller;

import static com.nc.med.util.WebUrl.ADVANCED_SEARCH;
import static com.nc.med.util.WebUrl.PRODUCTS;
import static com.nc.med.util.WebUrl.PRODUCTS_ADD;
import static com.nc.med.util.WebUrl.PRODUCTS_REMOVE;
import static com.nc.med.util.WebUrl.PRODUCT_AND_CATEGORYID;
import static com.nc.med.util.WebUrl.PRODUCT_BY_PRODUCTID;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nc.med.Beans.ProductBean;
import com.nc.med.exception.CustomErrorType;
import com.nc.med.model.Cart;
import com.nc.med.model.Category;
import com.nc.med.model.Product;
import com.nc.med.service.CartService;
import com.nc.med.service.CategoryService;
import com.nc.med.service.ProductService;

@RestController
public class ProductController {
	private final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	ProductService productService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	public CartService cartService;

	@PostMapping(PRODUCTS)
	public ResponseEntity<?> saveProducts(@RequestBody List<Product> productList) {
		if (productList == null) {
			return new ResponseEntity<>(new CustomErrorType("input is empty"), HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(productService.saveProducts(productList), HttpStatus.CREATED);
	}

	@PostMapping(PRODUCT_AND_CATEGORYID)
	public ResponseEntity<?> saveProduct(@PathVariable int categoryID, @RequestBody Product product) {
		Category category = categoryService.findByCategoryID(categoryID);
		product.setCategory(category);
		Product productName = productService.findByProductName(product.getProductName());
		if (productName != null) {
			LOGGER.info("Product name already exist!!");
			return new ResponseEntity<>(new CustomErrorType("Product name already exist!!"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(productService.saveProduct(product), HttpStatus.CREATED);
	}

	@DeleteMapping(PRODUCT_BY_PRODUCTID)
	public ResponseEntity<?> deleteProduct(@PathVariable Integer productID) {
		Product product = productService.findByProductID(productID);
		if (product == null) {
			return new ResponseEntity<>(new CustomErrorType("ProductID: " + productID + " not found."),
					HttpStatus.NOT_FOUND);
		}
		productService.deleteProduct(productID);
		return new ResponseEntity<>(product, HttpStatus.OK);
	}

	@GetMapping(PRODUCT_BY_PRODUCTID)
	public ResponseEntity<?> getProductsById(@PathVariable int productID) {
		Product product = productService.findByProductID(productID);
		if (product == null) {
			return new ResponseEntity<>(new CustomErrorType("ProductID: " + productID + " not found."),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(product, HttpStatus.OK);
	}

	@GetMapping(ADVANCED_SEARCH)
	public ResponseEntity<List<ProductBean>> getProductsByCategoryId(@PathVariable Integer categoryID,
			@PathVariable String productName) {
		return new ResponseEntity<>(productService.advancedSearch(categoryID, productName), HttpStatus.OK);
	}

	@GetMapping(PRODUCTS)
	public ResponseEntity<?> getProductsForAllCategories() {
		return new ResponseEntity<>(productService.findAllProduct(), HttpStatus.OK);
	}

	@PostMapping(PRODUCTS_ADD)
	public ResponseEntity<?> addCartList(@RequestBody List<Product> products) {
		return new ResponseEntity<>(productService.addToStock(products), HttpStatus.OK);
	}

	@PostMapping(PRODUCTS_REMOVE)
	public ResponseEntity<?> removeCartList(@RequestBody List<Product> products) {
		for (Product product : products) {
			Cart cart = new Cart();
			cart.setProductName(product.getProductName());
			cart.setPrice(product.getPrice());
			cart.setQty(product.getQty());
			cartService.saveCart(cart);
		}
		return productService.removeFromStock(products);
	}
}