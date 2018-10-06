package com.fico.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fico.demo.model.PurchaseOrderDetail;

public interface OrderDetailRepo extends JpaRepository<PurchaseOrderDetail, Integer>{

}
