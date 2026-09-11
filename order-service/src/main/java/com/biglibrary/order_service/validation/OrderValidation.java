package com.biglibrary.order_service.validation;

import com.biglibrary.order_service.clients.ProductServiceClient;
import com.biglibrary.order_service.dto.OrderItemsDTO;
import com.biglibrary.order_service.dto.ProductDTO;
import com.biglibrary.order_service.exceptions.ObjectNotFoundException;
import com.biglibrary.order_service.request.CreateOrderRequest;
import com.biglibrary.order_service.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderValidation {

	private final ProductServiceClient productServiceClient;

	public void validateOrder(CreateOrderRequest createOrderRequest) {
		Set<OrderItemsDTO> orderItems = createOrderRequest.getOrderItems();
		for (OrderItemsDTO orderItem : orderItems) {
			ApiResponse apiResponse = productServiceClient.getProductByCode(orderItem.getCode())
					.orElseThrow(() -> new ObjectNotFoundException("Product not found"));
			ProductDTO productDTO = productServiceClient.extractProductDTO(Optional.of(apiResponse))
					.orElseThrow(() -> new ObjectNotFoundException("Product not found"));
			if (orderItem.getPrice().compareTo(productDTO.getPrice()) != 0) {
				log.error("Price mismatch for product code: {}", orderItem.getCode());
				throw new IllegalArgumentException("Price mismatch for product code: " + orderItem.getCode());
			}
		}
	}

}
