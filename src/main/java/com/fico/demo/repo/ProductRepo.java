package com.fico.demo.repo;

import com.fico.demo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Integer> {

	String FIND_BY_PRODUCT_NAME_QUERY = "SELECT * FROM Product p WHERE lower(p.productName) like %:productName";

	List<Product> findByCategoryCategoryID(int categoryID);

	void deleteByCategoryCategoryID(int categoryID);

	List<Product> findByCategoryCategoryIDAndProductNameContainingIgnoreCaseOrderByPrice(Integer categoryID,
                                                                                         String productName);

	List<Product> findByCategoryCategoryIDAndProductNameContainingIgnoreCaseOrderByPriceDesc(Integer categoryID,
                                                                                             String productName);

	List<Product> findByCategoryCategoryIDAndProductNameContainingIgnoreCaseOrderByProductIDDesc(Integer categoryID,
                                                                                                 String productName);

	List<Product> findByCategoryCategoryIDOrderByPrice(Integer categoryID);

	List<Product> findByCategoryCategoryIDOrderByPriceDesc(Integer categoryID);

	List<Product> findByCategoryCategoryIDOrderByProductIDDesc(Integer categoryID);

	Product findByProductName(String productName);

	List<Product> findAllByOrderByPrice();

	List<Product> findAllByOrderByPriceDesc();

	List<Product> findAllByOrderByProductIDDesc();

	String FIND_WITH_DESC_QUERY = "SELECT d, avg(e.star) FROM Rating e Right JOIN e.product d GROUP BY d";

	@Query(FIND_WITH_DESC_QUERY)
	public List<Product> findProductWithRating();

}