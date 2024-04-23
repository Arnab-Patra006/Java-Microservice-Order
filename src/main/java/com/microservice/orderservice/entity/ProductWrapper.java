package com.microservice.orderservice.entity;

import lombok.Data;

@Data
public class ProductWrapper {
	private Integer productid;
	private String productname;
	private Integer availability;
	private Double productPrice;
	private String category;
}
