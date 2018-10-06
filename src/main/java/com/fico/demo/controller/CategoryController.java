package com.fico.demo.controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fico.demo.exception.CustomErrorType;
import com.fico.demo.model.Category;
import com.fico.demo.repo.CategoryRepo;
import com.fico.demo.repo.ProductRepo;
import com.fico.demo.util.Utility;
import static com.fico.demo.util.WebUrl.CART_BY_CARTID;
import static com.fico.demo.util.WebUrl.CATEGORY;
import static com.fico.demo.util.WebUrl.CATEGORY_BY_NAME;

@RestController
public class CategoryController {

	public static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

	@Autowired
	public CategoryRepo categoryRepo;

	@Autowired
	public ProductRepo productRepo;

	@RequestMapping(value = CATEGORY, method = RequestMethod.POST)
	public ResponseEntity<Category> addCategory(@RequestParam("categoryData") String categoryData,
			@RequestParam("file") MultipartFile file) {
		Category cat = new Category();
		try {
			byte[] bytes = file.getBytes();
			cat.setImage(bytes);
		} catch (IOException e) {
			logger.error("File is failed to save", e);
			return new ResponseEntity("File is failed to save", HttpStatus.BAD_REQUEST);
		}

		JsonNode jn = Utility.jsonToObject(categoryData);

		if (jn == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		String categoryName = jn.get("categoryName").asText();

		Category category1 = categoryRepo.findByCategoryName(categoryName);
		if (category1 != null) {
			return new ResponseEntity(new CustomErrorType("Category name already exist!!"), HttpStatus.NOT_FOUND);
		}

		cat.setCategoryName(categoryName);
		cat.setActive(true);
		Category category = categoryRepo.save(cat);

		if (category == null) {
			return new ResponseEntity(new CustomErrorType("Category is not saved"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(category, HttpStatus.CREATED);
	}

	@RequestMapping(value = CATEGORY + "/{categoryID}", method = RequestMethod.DELETE)
	public ResponseEntity<Category> deleteCategory(@PathVariable Integer categoryID) {
		Category category = categoryRepo.findOne(categoryID);
		if (category == null) {
			return new ResponseEntity(new CustomErrorType("Category with categoryID " + categoryID + " not found."),
					HttpStatus.NOT_FOUND);
		}
		categoryRepo.delete(categoryID);
		return new ResponseEntity<>(category, HttpStatus.OK);
	}

	@RequestMapping(value = CATEGORY, method = RequestMethod.GET)
	public ResponseEntity<List<Category>> findAllCategoryList() {
		return new ResponseEntity<>(categoryRepo.findAll(), HttpStatus.OK);
	}

	@RequestMapping(value = CATEGORY_BY_NAME + "{categoryName}", method = RequestMethod.GET)
	public ResponseEntity<List<Category>> findByCategoryName(@PathVariable String categoryName) {
		if (categoryName == null) {
			return new ResponseEntity(new CustomErrorType("input is empty"), HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(categoryRepo.findByCategoryNameContainingIgnoreCase(categoryName), HttpStatus.OK);
	}
}
