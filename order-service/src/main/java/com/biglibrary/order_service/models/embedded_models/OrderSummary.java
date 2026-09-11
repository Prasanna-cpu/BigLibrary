package com.biglibrary.order_service.models.embedded_models;

import com.biglibrary.order_service.enums.OrderStatus;

public record OrderSummary(String orderNumber, OrderStatus status) {
}