package com.fico.demo.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fico.demo.SpringAppLauncher;
import com.fico.demo.model.Category;
import com.fico.demo.model.Product;
import com.fico.demo.repo.CategoryRepo;
import com.fico.demo.repo.ProductRepo;
import com.fico.demo.util.WebUrl;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = SpringAppLauncher.class)
@Profile("test")
public class ProductControllerTest {

	@InjectMocks
	ProductController controller;

	@Autowired
	WebApplicationContext context;

	private MockMvc mockMvc;

	@Autowired
	public ProductRepo productRepo;

	@Autowired
	private CategoryRepo categoryRepo;

	@Before
	public void initTests() {
		this.mockMvc = webAppContextSetup(context).build();
		//initializeEmployeeData();
	}

	@Test
	public void getProductListTest() throws Exception {
		ResultActions result = mockMvc.perform(get(WebUrl.PRODUCT).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(1)));
		result.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void saveProductTest() throws Exception {
		String input = "\"productData\":{\"id\":1,\"name\":\"Raj\",\"age\":58,\"salary\":1000,\"company\":\"FICO\"}";
		ResultActions result = mockMvc.perform(post(WebUrl.PRODUCT).content(input).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(1)));
		result.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void getEmployeeTest() throws Exception {
		long id = 1;
		ResultActions result = mockMvc.perform(get(WebUrl.PRODUCT)).andExpect(status().isOk());
		result.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void getProductsByCategoryIdTest() throws Exception {
		long id = 1;
		ResultActions result = mockMvc.perform(get(WebUrl.PRODUCT_BY_CATEGORYID_SORTTYPE + id)).andExpect(status().isOk());
		result.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void getProductsByName() throws Exception {
		initializeEmployeeData();
		String name = "Item1";
		ResultActions result = mockMvc.perform(get(WebUrl.PRODUCT_BY_NAME_CATEGORYID_PRODUCTNAME_SORTTYPE + name)).andExpect(status().isOk());
		result.andDo(MockMvcResultHandlers.print());
	}

	private void initializeEmployeeData() {
		Category category = new Category();
		category.setCategoryName("Mobiles");
		categoryRepo.save(category);

		Category category1 = new Category();
		category1.setCategoryName("Dress");
		//categoryRepo.save(category1);

		String image = null;

		Product product = new Product();
		product.setProductName("Item1");
		product.setPrice(600);
		product.setCategory(category);
		product.setImage(image.getBytes());
		productRepo.save(product);

		Product product1 = new Product();
		product1.setProductName("Item2");
		product1.setPrice(600);
		product1.setCategory(category);
		product1.setImage(image.getBytes());
		productRepo.save(product1);

		Product product2 = new Product();
		product2.setProductName("Item3");
		product2.setPrice(600);
		product2.setCategory(category1);
		product2.setImage(image.getBytes());
		productRepo.save(product2);
	}

	private byte[] toJson(Object r) throws Exception {
		ObjectMapper map = new ObjectMapper();
		return map.writeValueAsString(r).getBytes();
	}
}
