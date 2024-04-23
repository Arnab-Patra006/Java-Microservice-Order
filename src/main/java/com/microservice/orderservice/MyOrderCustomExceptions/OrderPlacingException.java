package com.microservice.orderservice.MyOrderCustomExceptions;

public class OrderPlacingException extends Exception {
	public OrderPlacingException(String errorMessage) {
		super(errorMessage);
	}
}
