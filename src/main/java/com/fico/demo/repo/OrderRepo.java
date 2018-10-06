package com.fico.demo.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fico.demo.model.PurchaseOrder;

public interface OrderRepo extends JpaRepository<PurchaseOrder, Integer> {

	PurchaseOrder findOneByOrderNo(String orderNo);

	List<PurchaseOrder> findAllOrdersByUserID(int userId);

	List<PurchaseOrder> findAllOrdersByorderBookingDateBetween(Date fromDate, Date toDate);
}
