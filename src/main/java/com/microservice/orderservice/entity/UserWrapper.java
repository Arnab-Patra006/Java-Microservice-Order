package com.microservice.orderservice.entity;

import lombok.Data;

@Data
public class UserWrapper {
	private Integer userId;
	private String username;
	private String email;
	private String password;
}
