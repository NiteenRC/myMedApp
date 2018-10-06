/*package com.fico.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.fico.demo.model.Product;
import com.fico.demo.repo.ProductRepo;
import com.fico.demo.service.ProductServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

	@Mock
	private static ProductRepo productRepo;

	@InjectMocks
	private ProductServiceImpl productServiceImpl;

	@Test
	public void findByProductName() {
		Product product = new Product();
		product.setProductName("a");
		List<Product> products = new ArrayList<>();
		products.add(product);
		
		Mockito.when(productRepo.findByProductNameContainingIgnoreCase("a")).thenReturn(products);

		List<Product> p = productServiceImpl.findByProductNameContainingIgnoreCase(product.getProductName());

		Assert.assertEquals(products, p);
	}
}
*/