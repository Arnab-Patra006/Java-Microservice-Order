package com.microservice.orderservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.orderservice.MyOrderCustomExceptions.InsufficientCartException;
import com.microservice.orderservice.MyOrderCustomExceptions.MyUserByIdNotFoundException;
import com.microservice.orderservice.MyOrderCustomExceptions.OrderFindingException;
import com.microservice.orderservice.MyOrderCustomExceptions.OrderPlacingException;
import com.microservice.orderservice.MyOrderCustomExceptions.UpdateProductTableException;
import com.microservice.orderservice.entity.Order;
import com.microservice.orderservice.entity.Status;
import com.microservice.orderservice.service.OrderService;

@RestController
@RequestMapping("order")
public class OrderController {

	@Autowired
	OrderService orderservice;

	@PostMapping("/place")
	public ResponseEntity<String> placeMyOrder(@RequestBody Order order) throws MyUserByIdNotFoundException,
			OrderFindingException, InsufficientCartException, UpdateProductTableException, OrderPlacingException {
		return orderservice.placeOrder(order);
	}

	@GetMapping("/all")
	public ResponseEntity<List<Order>> showAllOrders() throws Exception {
		return orderservice.getAllOrder();
	}

	@GetMapping("/{orderid}")
	public ResponseEntity<Order> showOrderById(@PathVariable Integer orderid) throws Exception {
		return orderservice.getOrderByOrderId(orderid);
	}

	@GetMapping("/user/{userid}")
	public ResponseEntity<List<Order>> showOrderOfIndividualUser(@PathVariable Integer userid) throws Exception {
		return orderservice.getOrderByUserId(userid);
	}

	@GetMapping("/user")
	public ResponseEntity<List<Order>> showOrderOfIndividualUserByOrderStatus(@RequestParam Integer userid,
			@RequestParam Status status) throws Exception {
		return orderservice.getOrderByUserIdByStatus(userid, status);
	}
}
