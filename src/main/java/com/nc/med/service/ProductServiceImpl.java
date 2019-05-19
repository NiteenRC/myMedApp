package com.nc.med.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nc.med.Beans.ProductBean;
import com.nc.med.exception.CustomErrorType;
import com.nc.med.model.Cart;
import com.nc.med.model.Product;
import com.nc.med.repo.CartRepo;
import com.nc.med.repo.ProductRepo;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductRepo productRepo;

	@Autowired
	CartRepo cartRepo;

	@Autowired
	public CartService cartService;

	List<Product> productsList = null;

	@Override
	public List<ProductBean> advancedSearch(Integer categoryID, String productName) {
		List<Product> products = null;
		if (categoryID > 0 && !productName.equals("null")) {
			products = productRepo.findByCategoryCategoryIDAndProductNameContainingIgnoreCase(categoryID, productName);
		} else if (categoryID > 0 && productName.equals("null")) {
			products = productRepo.findByCategoryCategoryID(categoryID);
		} else if (categoryID == 0 && !productName.equals("null")) {
			products = productRepo.findByProductNameContainingIgnoreCase(productName);
		}
		return fetchAllProducts(products);
	}

	@Override
	public List<ProductBean> fetchAllProducts(List<Product> products) {
		List<ProductBean> productBeans = new ArrayList<>();
		ProductBean productBean = null;
		for (Product product : products) {
			productBean = new ProductBean();
			productBean.setProductName(product.getProductName());
			productBean.setPrice(product.getPrice());
			productBean.setProductDesc(product.getProductDesc());
			productBean.setQty(product.getQty());
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
	public List<Product> findAllProduct() {
		return productRepo.findAll();
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
			if (product.getProductName() != null) {
				Product product2 = productRepo.findByProductName(product.getProductName());
				if (product2 == null) {
					productRepo.save(product);
				} else {
					product2.setQty(product2.getQty() + product.getQty());
					productRepo.save(product2);
				}
			}
		}
		return null;
	}

	@Override
	public ResponseEntity<?> removeFromStock(List<Product> products) {
		for (Product product : products) {
			if (product.getProductName() != null) {
				Product product2 = productRepo.findByProductName(product.getProductName());
				// deduct from stock
				product2.setQty(product2.getQty() - product.getQty());
				productRepo.save(product2);

				// Add to cart
				Cart cart = new Cart();
				cart.setProductName(product.getProductName());
				cart.setPrice(product.getPrice());
				cart.setQty(product.getQty());
				cartService.saveCart(cart);
			}
		}
		return null;
	}

	@Override
	public ResponseEntity<?> removeProductTemp(List<Product> products) {
		boolean validation = true;
		for (Product product : products) {
			Product product2 = productRepo.findByProductName(product.getProductName());
			if (product.getProductName() != null) {
				if (product2 == null) {
					validation = false;
					return new ResponseEntity<>(
							new CustomErrorType("Stock is not avaible for " + product.getProductName()), HttpStatus.OK);
				} else {
					if (product2.getQty() < product.getQty()) {
						validation = false;
						return new ResponseEntity<>(
								new CustomErrorType(
										"Stock avaible for " + product.getProductName() + " is " + product2.getQty()),
								HttpStatus.OK);
					}
				}
			}
		}
		if (validation) {
			productsList = new ArrayList<>();
			productsList.addAll(products);
			return new ResponseEntity<>(productsList, HttpStatus.OK);
		}
		return null;
	}

	@Override
	public List<Product> removeProductGetTemp() {
		return productsList;
	}
}
