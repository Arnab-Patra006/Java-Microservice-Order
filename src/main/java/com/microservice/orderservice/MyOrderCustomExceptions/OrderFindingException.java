package com.microservice.orderservice.MyOrderCustomExceptions;

public class OrderFindingException extends Exception {
	public OrderFindingException(String errorMessage) {
		super(errorMessage);
	}
}
