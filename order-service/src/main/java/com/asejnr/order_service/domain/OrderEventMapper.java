package com.asejnr.order_service.domain;

import com.asejnr.order_service.domain.model.OrderCreatedEvent;
import com.asejnr.order_service.domain.model.OrderDeliveredEvent;
import com.asejnr.order_service.domain.model.OrderItem;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

class OrderEventMapper {
    static OrderCreatedEvent buildOrderCreatedEvent(OrderEntity order) {
        return new OrderCreatedEvent(
                UUID.randomUUID().toString(),
                order.getOrderNumber(),
                getOrderItems(order),
                order.getCustomer(),
                order.getDeliveryAddress(),
                LocalDateTime.now()
        );
    }

    static OrderDeliveredEvent buildOrderDeliveredEvent(OrderEntity order) {
        return new OrderDeliveredEvent(
                UUID.randomUUID().toString(),
                order.getOrderNumber(),
                getOrderItems(order),
                order.getCustomer(),
                order.getDeliveryAddress(),
                LocalDateTime.now()
        );
    }

    private static Set<OrderItem> getOrderItems(OrderEntity order) {
        return order.getItems().stream()
                .map(item -> new OrderItem(item.getCode(), item.getName(), item.getPrice(), item.getQuantity()))
                .collect(Collectors.toSet());
    }
}
