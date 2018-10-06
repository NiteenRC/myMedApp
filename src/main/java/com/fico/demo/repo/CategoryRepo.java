package com.fico.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fico.demo.model.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

	Category findByCategoryName(String categoryName);

	@Query(value = "SELECT category, COUNT(*) as count FROM Product GROUP BY category", nativeQuery = true)
	int findCount();

	List<Category> findByCategoryNameContainingIgnoreCase(String categoryName);
	
	//List<Category> findAllByCategory();
}
