package com.asejnr.order_service.domain;

import com.asejnr.order_service.domain.model.CreateOrderRequest;
import com.asejnr.order_service.domain.model.OrderDTO;
import com.asejnr.order_service.domain.model.OrderItem;
import com.asejnr.order_service.domain.model.OrderStatus;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

class OrderMapper {
    static OrderEntity convertOrderToEntity(CreateOrderRequest orderRequest) {
        OrderEntity order = new OrderEntity();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setStatus(OrderStatus.NEW);
        order.setCustomer(orderRequest.customer());
        order.setDeliveryAddress(orderRequest.deliveryAddress());

        Set<OrderItemEntity> orderItems = new HashSet<>();

        for(OrderItem item : orderRequest.items()) {
            OrderItemEntity orderItem = new OrderItemEntity();
            orderItem.setCode(item.code());
            orderItem.setName(item.name());
            orderItem.setPrice(item.price());
            orderItem.setQuantity(item.quantity());
            orderItem.setOrder(order);
            orderItems.add(orderItem);
        }
        order.setItems(orderItems);
        return order;
    }

    static OrderDTO convertEntityToDTO(OrderEntity orderEntity) {
        Set<OrderItem> orderItems = orderEntity.getItems().stream()
                .map(item -> new OrderItem(item.getCode(), item.getName(), item.getPrice(), item.getQuantity()))
                .collect(Collectors.toSet());

        return new OrderDTO(
                orderEntity.getOrderNumber(),
                orderEntity.getUserName(),
                orderItems,
                orderEntity.getCustomer(),
                orderEntity.getDeliveryAddress(),
                orderEntity.getStatus(),
                orderEntity.getComments(),
                orderEntity.getCreatedAt());
    }
}
