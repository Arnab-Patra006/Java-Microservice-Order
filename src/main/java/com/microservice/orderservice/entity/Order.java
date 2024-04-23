package com.microservice.orderservice.entity;

import java.time.LocalDateTime;
import java.util.List;
import org.hibernate.annotations.CreationTimestamp;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "orders")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id")
	private Integer orderId;

	@Column(name = "user_id")
	private Integer userId;

	@ElementCollection
	private List<ProductQuantity> productQty;

	@Column(name = "order_status")
	private Status orderStatus;

	@CreationTimestamp
	@Column(name = "modification_date_time")
	private LocalDateTime modificationDate;
}
