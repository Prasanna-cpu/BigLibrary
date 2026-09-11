package com.biglibrary.order_service.mapper;

import com.biglibrary.order_service.dto.OrdersDTO;
import com.biglibrary.order_service.enums.OrderStatus;
import com.biglibrary.order_service.models.Orders;
import com.biglibrary.order_service.request.CreateOrderRequest;

import java.util.UUID;
import java.util.stream.Collectors;

public class OrderMapper {

	public static OrdersDTO mapToOrdersDTO(Orders orders) {
		OrdersDTO ordersDTO = new OrdersDTO();

		ordersDTO.setId(orders.getId());
		ordersDTO.setUserName(orders.getUserName());
		ordersDTO.setOrderNumber(orders.getOrderNumber());
		ordersDTO.setDeliveryAddress(AddressMapper.mapToAddressDTO(orders.getDeliveryAddress()));
		ordersDTO.setOrderItems(
				orders.getOrderItems().stream().map(OrderItemsMapper::mapToOrderItemsDTO).collect(Collectors.toSet()));
		ordersDTO.setCustomer(CustomerMapper.mapToCustomerDTO(orders.getCustomer()));
		ordersDTO.setStatus(orders.getStatus());
		ordersDTO.setComments(orders.getComments());

		return ordersDTO;

	}

	public static Orders mapToOrders(OrdersDTO ordersDTO) {
		Orders orders = new Orders();

		if (ordersDTO.getId() != null) {
			orders.setId(ordersDTO.getId());
		}

		orders.setOrderNumber(ordersDTO.getOrderNumber());
		orders.setUserName(ordersDTO.getUserName());
		orders.setDeliveryAddress(AddressMapper.mapToAddress(ordersDTO.getDeliveryAddress()));
		orders.setCustomer(CustomerMapper.mapToCustomer(ordersDTO.getCustomer()));
		orders.setStatus(ordersDTO.getStatus());
		orders.setComments(ordersDTO.getComments());

		return orders;

	}

	public static Orders mapToOrders(CreateOrderRequest createOrderRequest) {
		Orders orders = new Orders();

		if (createOrderRequest.getOrderItems() != null) {
			orders.setOrderItems(createOrderRequest.getOrderItems().stream().map(OrderItemsMapper::mapToOrderItems)
					.peek(orderItem -> orderItem.setOrder(orders)).collect(Collectors.toSet()));
		}
		orders.setCustomer(CustomerMapper.mapToCustomer(createOrderRequest.getCustomer()));
		orders.setDeliveryAddress(AddressMapper.mapToAddress(createOrderRequest.getAddress()));
		orders.setStatus(OrderStatus.NEW);
		orders.setOrderNumber(UUID.randomUUID().toString());

		return orders;

	}

}
