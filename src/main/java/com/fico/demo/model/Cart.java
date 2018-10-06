package com.fico.demo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Cart implements Serializable {
	private static final long serialVersionUID = -1000119078147252957L;

	@Id
	@GeneratedValue
	private int cartID;
	// @Column(length = 255, nullable = false)
	private String productName;
	@Column(name = "Price", nullable = false)
	private double price;
	private int qty;
	@Lob
	@Column(name = "Image", length = Integer.MAX_VALUE)
	private byte[] image;
	// @OneToOne // (cascade = CascadeType.ALL)
	private int productID;
	// @OneToOne // One cart can have multiple users
	// @JoinColumn
	private int userID;

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public Cart() {
	}

	public Cart(String productName, double price, byte[] image) {
		super();
		this.productName = productName;
		this.price = price;
		this.image = image;
	}

	public int getProductID() {
		return productID;
	}

	public void setProductID(int productID) {
		this.productID = productID;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public int getCartID() {
		return cartID;
	}

	public void setCartID(int cartID) {
		this.cartID = cartID;
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