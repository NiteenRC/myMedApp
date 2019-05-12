package com.nc.med.controller;

import com.nc.med.exception.CustomErrorType;
import com.nc.med.model.Category;
import com.nc.med.repo.ProductRepo;
import com.nc.med.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.nc.med.util.WebUrl.CATEGORY;

@RestController
public class CategoryController {

    public static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    public CategoryService categoryService;

    @Autowired
    public ProductRepo productRepo;

    @RequestMapping(value = CATEGORY, method = RequestMethod.POST)
    public ResponseEntity<Category> addCategory(@RequestBody Category category) {
        if (category == null) {
            return new ResponseEntity(new CustomErrorType("Category is not saved"), HttpStatus.NOT_FOUND);
        }

        Category category1 = categoryService.findByCategoryName(category.getCategoryName());
        if (category1 != null) {
            return new ResponseEntity(new CustomErrorType("Category name already exist!!"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(categoryService.saveCategory(category), HttpStatus.CREATED);
    }

    @RequestMapping(value = CATEGORY + "/{categoryID}", method = RequestMethod.DELETE)
    public ResponseEntity<Category> deleteCategory(@PathVariable Integer categoryID) {
        Category category = categoryService.findByCategoryID(categoryID);
        if (category == null) {
            return new ResponseEntity(new CustomErrorType("Category with categoryID " + categoryID + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        categoryService.deleteCategory(categoryID);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @RequestMapping(value = CATEGORY, method = RequestMethod.GET)
    public ResponseEntity<List<Category>> findAllCategoryList() {
        return new ResponseEntity<>(categoryService.fetchAllCategories(), HttpStatus.OK);
    }
}
