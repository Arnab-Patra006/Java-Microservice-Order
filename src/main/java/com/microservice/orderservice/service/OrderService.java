package com.microservice.orderservice.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.microservice.orderservice.MyOrderCustomExceptions.InsufficientCartException;
import com.microservice.orderservice.MyOrderCustomExceptions.MyUserByIdNotFoundException;
import com.microservice.orderservice.MyOrderCustomExceptions.OrderFindingException;
import com.microservice.orderservice.MyOrderCustomExceptions.OrderPlacingException;
import com.microservice.orderservice.MyOrderCustomExceptions.UpdateProductTableException;
import com.microservice.orderservice.entity.Order;
import com.microservice.orderservice.entity.Status;

public interface OrderService {
	public ResponseEntity<String> placeOrder(Order order) throws MyUserByIdNotFoundException, OrderFindingException,
			InsufficientCartException, UpdateProductTableException, OrderPlacingException;

	public ResponseEntity<List<Order>> getAllOrder() throws OrderFindingException;

	public ResponseEntity<Order> getOrderByOrderId(Integer orderid) throws OrderFindingException;

	public ResponseEntity<List<Order>> getOrderByUserId(Integer userid) throws OrderFindingException;

	public ResponseEntity<List<Order>> getOrderByUserIdByStatus(Integer userid, Status status)
			throws OrderFindingException;
}
