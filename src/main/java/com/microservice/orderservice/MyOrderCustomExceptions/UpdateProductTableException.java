package com.microservice.orderservice.MyOrderCustomExceptions;

public class UpdateProductTableException extends Exception {
	public UpdateProductTableException(String errorMessage) {
		super(errorMessage);
	}
}
