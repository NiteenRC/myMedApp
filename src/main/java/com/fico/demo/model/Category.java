package com.fico.demo.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Category {

	@Id
	@GeneratedValue
	@Column(name = "CATEGORY_ID", nullable = false)
	private int categoryID;
	//@Column(name = "CATEGORY_NAME", nullable = false)
	private String categoryName;
	//@Column(name = "CATEGORY_DESC")
	private String categoryDesc;
	@Lob
	@Column(name = "IMAGE", length = Integer.MAX_VALUE)
	private byte[] image;
	//@Column(name = "ACTIVE", nullable = false)
	private boolean active;
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "category", cascade = CascadeType.ALL)
	private Set<Product> products = new HashSet<>();

	public Category() {
	}

	public Category(String categoryName, byte[] image) {
		super();
		this.categoryName = categoryName;
		this.image = image;
	}

	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

	public int getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryDesc() {
		return categoryDesc;
	}

	public void setCategoryDesc(String categoryDesc) {
		this.categoryDesc = categoryDesc;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
