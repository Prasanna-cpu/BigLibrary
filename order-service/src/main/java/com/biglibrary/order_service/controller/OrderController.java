package com.biglibrary.order_service.controller;

import com.biglibrary.order_service.dto.OrdersDTO;
import com.biglibrary.order_service.models.embedded_models.OrderSummary;
import com.biglibrary.order_service.request.CreateOrderRequest;
import com.biglibrary.order_service.request.CreateOrderResponse;
import com.biglibrary.order_service.response.ApiResponse;
import com.biglibrary.order_service.service.OrderService;
import com.biglibrary.order_service.service.SecurityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {
	private final OrderService orderService;
	private final SecurityService securityService;

	@PostMapping("/create")
	@ResponseStatus(HttpStatus.CREATED)
	public CreateOrderResponse createOrderHandler(@Valid @RequestBody CreateOrderRequest request) {
		String userName = securityService.getLoginUsername();
		return orderService.createOrder(userName, request);
	}

	@GetMapping("/all")
	@ResponseStatus(HttpStatus.OK)
	List<OrderSummary> getOrders() {
		String userName = securityService.getLoginUsername();
		log.info("Fetching orders for user: {}", userName);
		return orderService.findOrders(userName);
	}

	@GetMapping("/order/{orderNumber}")
	public ResponseEntity<ApiResponse> getOrderByOrderNumberHandler(@PathVariable String orderNumber) {
		String userName = securityService.getLoginUsername();
		OrdersDTO orderDTO = orderService.getOrderByOrderNumber(orderNumber, userName);
		log.info("Fetching order for user: {}", userName);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ApiResponse(orderDTO, HttpStatus.OK.value(), HttpStatus.OK, "Order Found"));
	}

}
