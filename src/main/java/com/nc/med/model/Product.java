package com.nc.med.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;

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
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CATEGORY_ID", foreignKey = @ForeignKey(name = "PRODUCT_FK"))
	@JsonIgnore
	private Category category;
	private String productDesc;
	private int qty;
	@CreationTimestamp
	@Temporal(TemporalType.DATE)
	private Date date;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

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

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public Product() {
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
}