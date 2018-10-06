package com.fico.demo.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
// @Table(name = "Products")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Product implements Serializable {
	private static final long serialVersionUID = -1000119078147252957L;

	@Id
	@GeneratedValue
	@Column(name = "PRODUCT_ID", nullable = false)
	private int productID;
	@Column(length = 255, nullable = false)
	private String productName;
	@Column(name = "Price", nullable = false)
	private double price;
	@Lob
	@Column(name = "Image", length = Integer.MAX_VALUE)
	private byte[] image;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CATEGORY_ID", foreignKey = @ForeignKey(name = "PRODUCT_FK"))
	@JsonIgnore
	private Category category;
	private String productDesc;
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "product", cascade = CascadeType.ALL)
	private Set<Rating> ratings = new HashSet<>();
	/*
	 * @ManyToOne(fetch = FetchType.LAZY)
	 * 
	 * @JoinColumn(name = "CART_ID", foreignKey = @ForeignKey(name =
	 * "PRODUCT__CART_FK"))
	 * 
	 * @JsonIgnore private Cart cart;
	 * 
	 * //bi-directional many-to-one association to ProductImage
	 * 
	 * @OneToMany(mappedBy = "product") private List<ProductImage>
	 * productImages;
	 */

	public Set<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(Set<Rating> ratings) {
		this.ratings = ratings;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public Product() {
	}

	public Product(String productName, double price, byte[] image, Category category) {
		super();
		this.productName = productName;
		this.price = price;
		this.image = image;
		this.category = category;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public int getProductID() {
		return productID;
	}

	public void setProductID(int productID) {
		this.productID = productID;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}
}