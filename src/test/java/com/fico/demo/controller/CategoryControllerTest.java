package com.fico.demo.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fico.demo.model.Category;
import com.fico.demo.model.Product;
import com.fico.demo.model.User;
import com.fico.demo.repo.CategoryRepo;
import com.fico.demo.repo.ProductRepo;
import com.fico.demo.repo.UserRepo;
import com.fico.demo.util.WebUrl;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CategoryController.class, secure = false)
public class CategoryControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private CategoryRepo categoryRepo;

	@MockBean
	private ProductRepo productRepo1;

	@MockBean
	private UserRepo userRepo1;

	private Category category;
	private List<Category> categoryList = new ArrayList<>();

	@Before
	public void prepare() {
		User user = new User();
		user.setUserId(1);
		user.setUserName("Niteen");
		user.setUserEmail("admin@gmail.com");
		user.setUserPassword("admin");
		user.setUserType("A");
		userRepo1.save(user);

		Product product1 = new Product();
		product1.setProductID(1);
		product1.setProductName("Apple");
		product1.setPrice(25000);

		Product product2 = new Product();
		product2.setProductID(2);
		product2.setProductName("XOLO");
		product2.setPrice(15000);

		Set<Product> productList = new HashSet<>();
		productList.add(product1);
		productList.add(product2);

		productRepo1.save(productList);

		category = new Category();
		category.setCategoryID(1);
		category.setCategoryName("Mobiles");
		category.setProducts(productList);

		categoryList.add(category);

		categoryRepo.save(categoryList);
	}

	@Test
	public void findAllCategoryListTest() throws Exception {
		given(categoryRepo.findAll()).willReturn(categoryList);
		mvc.perform(get(WebUrl.CATEGORY).accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
		// .andExpect(jsonPath("$[0].categoryID", is(1)));
	}

	@Test
	public void findByCategoryNameTest() throws Exception {
		given(categoryRepo.findByCategoryNameContainingIgnoreCase("mob")).willReturn(categoryList);
		mvc.perform(get(WebUrl.CATEGORY_BY_NAME + "/mob").accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk());
	}
}
