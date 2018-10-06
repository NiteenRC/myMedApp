package com.fico.demo.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
// @Table(name = "Orders")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class PurchaseOrder implements Serializable {
	private static final long serialVersionUID = -2576670215015463100L;

	@Id
	@GeneratedValue
	@Column(name = "ORDER_ID", nullable = false)
	private int orderID;
	private String orderNo;
	@Column(name = "Amount")
	private double amount;
	private String firstName;
	private String lastName;
	private String address;
	private String emailAddress;
	private String phoneNumber;
	private String city;
	private String state;
	private String country;
	private String postalCode;
	@Temporal(TemporalType.DATE)
	private Date orderBookingDate;
	@Temporal(TemporalType.DATE)
	private Date orderCancelDate;
	private char orderStatus;
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "order", cascade = CascadeType.ALL)
	private Set<PurchaseOrderDetail> purchaseOrderDetail = new HashSet<>();
	private int userID;
	
	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public Set<PurchaseOrderDetail> getPurchaseOrderDetail() {
		return purchaseOrderDetail;
	}

	public void setPurchaseOrderDetail(Set<PurchaseOrderDetail> purchaseOrderDetail) {
		this.purchaseOrderDetail = purchaseOrderDetail;
	}

	public Date getOrderBookingDate() {
		return orderBookingDate;
	}

	public void setOrderBookingDate(Date orderBookingDate) {
		this.orderBookingDate = orderBookingDate;
	}

	public Date getOrderCancelDate() {
		return orderCancelDate;
	}

	public void setOrderCancelDate(Date orderCancelDate) {
		this.orderCancelDate = orderCancelDate;
	}

	public char getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(char orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public int getOrderID() {
		return orderID;
	}

	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}
