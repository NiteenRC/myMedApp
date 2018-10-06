package com.fico.demo.dto;

public class ProductDto {
	private int categoryID;
	private int productID;
	private int productCount;
	private String productName;
	private String categoryName;
	
	public ProductDto(int categoryID, int productID, int productCount, String productName, String categoryName) {
		super();
		this.categoryID = categoryID;
		this.productID = productID;
		this.productCount = productCount;
		this.productName = productName;
		this.categoryName = categoryName;
	}

	public int getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}

	public int getProductID() {
		return productID;
	}

	public void setProductID(int productID) {
		this.productID = productID;
	}

	public int getProductCount() {
		return productCount;
	}

	public void setProductCount(int productCount) {
		this.productCount = productCount;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
}
