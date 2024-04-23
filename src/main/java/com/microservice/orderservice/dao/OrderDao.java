package com.microservice.orderservice.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.microservice.orderservice.entity.Order;
import com.microservice.orderservice.entity.Status;

@Repository
public interface OrderDao extends JpaRepository<Order, Integer> {

	@Query(value = "SELECT * FROM ORDERS WHERE USER_ID=:userid", nativeQuery = true)
	List<Order> findOrderByUserId(Integer userid);

	@Query(value = "SELECT * FROM ORDERS WHERE USER_ID=:userid AND ORDER_STATUS=:status", nativeQuery = true)
	List<Order> findOrderByUserIdStatus(@Param("userid") Integer userid, @Param("status") Status status);
}
