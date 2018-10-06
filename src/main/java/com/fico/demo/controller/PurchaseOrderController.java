package com.fico.demo.controller;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fico.demo.exception.CustomErrorType;
import com.fico.demo.model.PurchaseOrder;
import com.fico.demo.model.PurchaseOrderDetail;
import com.fico.demo.repo.OrderDetailRepo;
import com.fico.demo.repo.OrderRepo;
import com.fico.demo.util.Utility;
import static com.fico.demo.util.WebUrl.ORDER;
import static com.fico.demo.util.WebUrl.ORDERS;
import static com.fico.demo.util.WebUrl.ORDER_BY_ORDERNO;
import static com.fico.demo.util.WebUrl.ORDER_BY_ORDERDATES;
import static com.fico.demo.util.WebUrl.ORDER_BY_USERID;
import com.fico.demo.vo.OrderVo;

@RestController
public class PurchaseOrderController {

	public static final Logger log = LoggerFactory.getLogger(PurchaseOrderController.class);

	@Autowired
	public OrderRepo orderRepo;

	@Autowired
	public OrderDetailRepo orderDetailRepo;

	@RequestMapping(value = ORDERS, method = RequestMethod.POST)
	public ResponseEntity<PurchaseOrder> addCartList(@RequestBody OrderVo orderVo) {
		PurchaseOrder order = orderVo.getPurchaseOrder();
		order.setOrderBookingDate(new Date());
		order.setOrderNo("ORDERNO" + Utility.nextSessionId());
		order.setUserID(orderVo.getUserID());
		PurchaseOrder oder = orderRepo.save(order);

		List<PurchaseOrderDetail> orderDetail = orderVo.getPurchaseOrderDetail();
		PurchaseOrderDetail purchaseOrderDetail = new PurchaseOrderDetail();

		for (PurchaseOrderDetail purchaseOrderDetails : orderDetail) {
			purchaseOrderDetail.setOrder(oder);
			orderDetailRepo.save(purchaseOrderDetails);
		}

		if (oder == null) {
			return new ResponseEntity(new CustomErrorType("Order is not done!!"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(oder, HttpStatus.CREATED);
	}

	@RequestMapping(value = ORDER, method = RequestMethod.POST)
	public ResponseEntity<PurchaseOrder> createOrder(@RequestBody PurchaseOrder purchaseOrder) {
		PurchaseOrder cartResponse = orderRepo.save(purchaseOrder);
		if (purchaseOrder == null) {
			return new ResponseEntity(new CustomErrorType("Order is not done!!"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(cartResponse, HttpStatus.CREATED);
	}

	@RequestMapping(value = ORDER_BY_ORDERNO, method = RequestMethod.GET)
	public ResponseEntity<PurchaseOrder> findByOrderNo(@PathVariable String orderNo) {
		return new ResponseEntity<>(orderRepo.findOneByOrderNo(orderNo), HttpStatus.OK);
	}

	@RequestMapping(value = ORDER_BY_ORDERDATES, method = RequestMethod.GET)
	public ResponseEntity<List<PurchaseOrder>> findByOrderDates(@PathVariable Date fromDate,
			@PathVariable Date toDate) {
		return new ResponseEntity<>(orderRepo.findAllOrdersByorderBookingDateBetween(fromDate, toDate), HttpStatus.OK);
	}

	@RequestMapping(value = ORDER, method = RequestMethod.GET)
	public ResponseEntity<List<PurchaseOrder>> findAllOrder() {
		return new ResponseEntity<>(orderRepo.findAll(), HttpStatus.OK);
	}

	@RequestMapping(value = ORDER_BY_USERID, method = RequestMethod.GET)
	public ResponseEntity<List<PurchaseOrder>> findAllOrdersByUser(@PathVariable int userID) {
		return new ResponseEntity<>(orderRepo.findAllOrdersByUserID(userID), HttpStatus.OK);
	}
}