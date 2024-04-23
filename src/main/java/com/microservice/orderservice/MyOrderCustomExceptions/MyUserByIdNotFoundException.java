package com.microservice.orderservice.MyOrderCustomExceptions;

public class MyUserByIdNotFoundException extends Exception {
	public MyUserByIdNotFoundException(String errorMessage) {
		super(errorMessage);
	}
}
