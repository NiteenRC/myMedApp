package com.nc.med.service;

import com.nc.med.model.Category;

import java.util.List;

public interface CategoryService {

    Category saveCategory(Category product);

    Category findByCategoryID(Integer categoryID);

    void deleteCategory(Integer CategoryID);

    Category findByCategoryName(String categoryName);

    List<Category> fetchAllCategories();

}
