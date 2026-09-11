package com.biglibrary.order_service.mapper;

import com.biglibrary.order_service.dto.OrderItemsDTO;
import com.biglibrary.order_service.models.OrderItems;

public class OrderItemsMapper {

	public static OrderItemsDTO mapToOrderItemsDTO(OrderItems orderItems) {
		if (orderItems == null) {
			return null;
		}
		OrderItemsDTO orderItemsDTO = new OrderItemsDTO();

		orderItemsDTO.setId(orderItems.getId());
		orderItemsDTO.setCode(orderItems.getCode());
		orderItemsDTO.setName(orderItems.getName());
		orderItemsDTO.setPrice(orderItems.getPrice());
		orderItemsDTO.setQuantity(orderItems.getQuantity());
		if (orderItems.getOrder() != null) {
			orderItemsDTO.setOrderId(orderItems.getOrder().getId());
		}

		return orderItemsDTO;

	}

	public static OrderItems mapToOrderItems(OrderItemsDTO orderItemsDTO) {
		if (orderItemsDTO == null) {
			return null;
		}
		OrderItems orderItems = new OrderItems();

		if (orderItemsDTO.getId() != null) {
			orderItems.setId(orderItemsDTO.getId());
		}

		orderItems.setCode(orderItemsDTO.getCode());
		orderItems.setName(orderItemsDTO.getName());
		orderItems.setPrice(orderItemsDTO.getPrice());
		orderItems.setQuantity(orderItemsDTO.getQuantity());

		return orderItems;
	}

}