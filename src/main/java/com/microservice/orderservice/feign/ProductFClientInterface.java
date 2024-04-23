package com.microservice.orderservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.microservice.orderservice.entity.ProductWrapper;

@FeignClient(name = "PRODUCT-SERVICE", url = "localhost:8090/product")
public interface ProductFClientInterface {
	@GetMapping("/{productid}")
	public ResponseEntity<ProductWrapper> getProductById(@PathVariable Integer productid);

	@PutMapping("/update/{productid}")
	public ResponseEntity<ProductWrapper> updateProduct(@PathVariable Integer productid,
			@RequestBody ProductWrapper product);

	@GetMapping("update/{productid}/{quantity}")
	public ResponseEntity<ProductWrapper> updateProductQuantity(@PathVariable Integer productid,
			@PathVariable Integer quantity);
}
