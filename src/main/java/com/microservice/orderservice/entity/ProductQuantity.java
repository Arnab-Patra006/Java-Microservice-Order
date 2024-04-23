package com.microservice.orderservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class ProductQuantity {
	@Column(name = "product_id")
	private Integer productId;
	@Column(name = "quantity")
	private Integer quantity;
}
