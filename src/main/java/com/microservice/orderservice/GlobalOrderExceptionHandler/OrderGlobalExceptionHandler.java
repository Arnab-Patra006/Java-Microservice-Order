package com.microservice.orderservice.GlobalOrderExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.microservice.orderservice.MyOrderCustomExceptions.InsufficientCartException;
import com.microservice.orderservice.MyOrderCustomExceptions.MyUserByIdNotFoundException;
import com.microservice.orderservice.MyOrderCustomExceptions.OrderFindingException;
import com.microservice.orderservice.MyOrderCustomExceptions.OrderPlacingException;
import com.microservice.orderservice.MyOrderCustomExceptions.UpdateProductTableException;

@ControllerAdvice
public class OrderGlobalExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(value = OrderFindingException.class)
	public ResponseEntity<String> OrderFindingExceptionF(OrderFindingException message) {
		return new ResponseEntity<String>(message.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = MyUserByIdNotFoundException.class)
	public ResponseEntity<String> MyUserByIdNotFoundExceptionF(MyUserByIdNotFoundException message) {
		return new ResponseEntity<String>(message.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = InsufficientCartException.class)
	public ResponseEntity<String> InsufficientCartExceptionF(InsufficientCartException message) {
		return new ResponseEntity<String>(message.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = OrderPlacingException.class)
	public ResponseEntity<String> OrderPlacingExceptionF(OrderPlacingException message) {
		System.out.println("Order placing exception");
		return new ResponseEntity<String>(message.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = UpdateProductTableException.class)
	public ResponseEntity<String> UpdateProductTableExceptionF(UpdateProductTableException message) {
		return new ResponseEntity<String>(message.getMessage(), HttpStatus.BAD_REQUEST);
	}
}
