package com.microservice.orderservice.MyOrderCustomExceptions;

public class InsufficientCartException extends Exception {
	public InsufficientCartException(String errorMessage) {
		super(errorMessage);
	}
}
