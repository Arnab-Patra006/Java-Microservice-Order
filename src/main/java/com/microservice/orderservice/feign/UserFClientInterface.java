package com.microservice.orderservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.microservice.orderservice.entity.UserWrapper;

@FeignClient(name = "USER-SERVICE", url = "localhost:8080/user")
public interface UserFClientInterface {
	@GetMapping("/{userid}")
	public ResponseEntity<UserWrapper> UserById(@PathVariable Integer userid);
}
