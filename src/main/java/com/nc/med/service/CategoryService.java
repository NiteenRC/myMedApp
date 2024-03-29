package com.nc.med.service;

import com.nc.med.model.Category;

import java.util.List;

public interface CategoryService {

    Category saveCategory(Category category);

    Category findByCategoryID(Integer categoryID);

    void deleteCategory(Category categoryID);

    Category findByCategoryName(String categoryName);

    List<Category> fetchAllCategories();

}
