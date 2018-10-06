package com.fico.demo.vo;

import java.util.List;

import com.fico.demo.model.PurchaseOrder;
import com.fico.demo.model.PurchaseOrderDetail;

public class OrderVo {
	private int userID;
	private PurchaseOrder purchaseOrder;
	private List<PurchaseOrderDetail> purchaseOrderDetail;

	public PurchaseOrder getPurchaseOrder() {
		return purchaseOrder;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}

	public List<PurchaseOrderDetail> getPurchaseOrderDetail() {
		return purchaseOrderDetail;
	}

	public void setPurchaseOrderDetail(List<PurchaseOrderDetail> purchaseOrderDetail) {
		this.purchaseOrderDetail = purchaseOrderDetail;
	}
}
