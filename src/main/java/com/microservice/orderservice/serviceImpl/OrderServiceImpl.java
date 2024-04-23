package com.microservice.orderservice.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.microservice.orderservice.MyOrderCustomExceptions.InsufficientCartException;
import com.microservice.orderservice.MyOrderCustomExceptions.MyUserByIdNotFoundException;
import com.microservice.orderservice.MyOrderCustomExceptions.OrderFindingException;
import com.microservice.orderservice.MyOrderCustomExceptions.OrderPlacingException;
import com.microservice.orderservice.MyOrderCustomExceptions.UpdateProductTableException;
import com.microservice.orderservice.dao.OrderDao;
import com.microservice.orderservice.entity.Order;
import com.microservice.orderservice.entity.ProductQuantity;
import com.microservice.orderservice.entity.ProductWrapper;
import com.microservice.orderservice.entity.Status;
import com.microservice.orderservice.entity.UserWrapper;
import com.microservice.orderservice.feign.ProductFClientInterface;
import com.microservice.orderservice.feign.UserFClientInterface;
import com.microservice.orderservice.service.OrderService;

import feign.FeignException.FeignClientException;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	OrderDao orderdao;

	@Autowired
	UserFClientInterface userclient;

	@Autowired
	ProductFClientInterface productclient;

	// (1)
	public ResponseEntity<String> placeOrder(Order order) throws MyUserByIdNotFoundException, OrderFindingException,
			InsufficientCartException, UpdateProductTableException, OrderPlacingException {
		try {
			Integer userid = order.getUserId();
			try {
				UserWrapper u = userclient.UserById(userid).getBody();
			} catch (FeignClientException e) {
				throw new MyUserByIdNotFoundException("This user id " + userid + " is invalid for requesting Order");
			}
			List<ProductQuantity> orderProducts = order.getProductQty();
			if (orderProducts.size() == 0) {
				throw new OrderPlacingException("Please Add Products to place order");
			}
			Status orderstatus = order.getOrderStatus();
			Integer allProductsAvailableFlag = 1;
			for (Integer i = 0; i < orderProducts.size(); i++) {
				try {
					Integer requestPid = orderProducts.get(i).getProductId();
					Integer requestPqty = orderProducts.get(i).getQuantity();
					ProductWrapper pwrap = productclient.getProductById(requestPid).getBody();
					if (requestPqty == 0) {
						throw new OrderPlacingException("Invalid Order Cancellation Request");
					}
					if (pwrap.getAvailability() < requestPqty) {
						allProductsAvailableFlag = 0;
						throw new InsufficientCartException(
								"You Cannot add Product more than " + pwrap.getAvailability());
					}
				} catch (FeignClientException e) {
					throw new InsufficientCartException("Insufficient Product to be Ordered.");
				}
			}
			if (orderstatus.equals(Status.CONFIRMED)) {
				if (allProductsAvailableFlag == 1) {
					try {
						for (Integer i = 0; i < orderProducts.size(); i++) {
							Integer requestPid = orderProducts.get(i).getProductId();
							Integer requestPqty = orderProducts.get(i).getQuantity();
							ProductWrapper pwrap = productclient.getProductById(requestPid).getBody();
							Integer available = pwrap.getAvailability();
							Integer remaining = available - requestPqty;
							try {
								productclient.updateProductQuantity(requestPid, remaining);
							} catch (Exception e) {
								throw new UpdateProductTableException(
										"Having issue while updating Product Table by product id " + requestPid
												+ " with quantity :" + remaining);
							}
						}
						try {
							orderdao.save(order);
						} catch (Exception e) {
							throw new OrderPlacingException("Invalid Order Placing Request");
						}
						return new ResponseEntity<>("Order Placed Successfully", HttpStatus.OK);
					} catch (OrderPlacingException e) {
						throw new OrderPlacingException("Invalid Order Placing Request");
					}
				}
			}
			if (orderstatus.equals(Status.CANCELLED)) {
				if (allProductsAvailableFlag == 1) {
					try {
						for (Integer i = 0; i < orderProducts.size(); i++) {
							Integer requestPid = orderProducts.get(i).getProductId();
							Integer requestPqty = orderProducts.get(i).getQuantity();
							ProductWrapper pwrap = productclient.getProductById(requestPid).getBody();
							Integer current_quantity = pwrap.getAvailability() + requestPqty;
							try {
								productclient.updateProductQuantity(requestPid, current_quantity);
							} catch (FeignClientException e) {
								throw new UpdateProductTableException(
										"Having issue while updating Product Table by product id " + requestPid
												+ " with quantity :" + current_quantity);
							}
						}
						try {
							orderdao.save(order);
						} catch (FeignClientException e) {
							throw new OrderPlacingException("Invalid Order Cancellation Request");
						}

						return new ResponseEntity<>("Order Cancelled Successfully", HttpStatus.OK);
					} catch (OrderPlacingException e) {
						throw new OrderPlacingException("Invalid Order Cancellation Request");
					}
				}
			}
		} catch (OrderPlacingException e) {
			throw new OrderPlacingException("Invalid Order Placing Request");
		}
		return null;
	}

	// (2)
	public ResponseEntity<List<Order>> getAllOrder() throws OrderFindingException {
		try {
			List<Order> ol = orderdao.findAll();
			if (ol.size() == 0) {
				throw new OrderFindingException("Currently Order Table is Empty");
			}
			return new ResponseEntity<>(ol, HttpStatus.OK);
		} catch (Exception e) {
			throw new OrderFindingException("Currently Order Table is Empty");
		}
	}

	// (3)
	public ResponseEntity<Order> getOrderByOrderId(Integer orderid) throws OrderFindingException {
		try {
			return new ResponseEntity<>(orderdao.findById(orderid).get(), HttpStatus.OK);
		} catch (Exception e) {
			throw new OrderFindingException("There is no such order present with order id :" + orderid);
		}
	}

	// (4)
	public ResponseEntity<List<Order>> getOrderByUserId(Integer userid) throws OrderFindingException {
		try {
			List<Order> userorders = orderdao.findOrderByUserId(userid);
			if (userorders.size() == 0) {
				System.out.println("OrderFindingException 1");
				throw new OrderFindingException("There is no such order present with user id :" + userid);
			}
			return new ResponseEntity<>(userorders, HttpStatus.OK);
		} catch (Exception e) {
			throw new OrderFindingException("There is no such order present with user id :" + userid);
		}
	}

	// (5)
	public ResponseEntity<List<Order>> getOrderByUserIdByStatus(Integer userid, Status status)
			throws OrderFindingException {
		try {
			List<Order> userorders = orderdao.findOrderByUserIdStatus(userid, status);
			if (userorders.size() == 0) {
				throw new OrderFindingException(
						"There is no such order present with user id :" + userid + " and status :" + status);
			}
			return new ResponseEntity<>(userorders, HttpStatus.OK);
		} catch (Exception e) {
			throw new OrderFindingException(
					"There is no such order present with user id :" + userid + " and status :" + status);
		}
	}
}
