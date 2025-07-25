package com.asejnr.order_service.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public record OrderDTO(
        String orderNumber,
        String username,
        Set<OrderItem> items,
        Customer customer,
        Address deliveryAddress,
        OrderStatus status,
        String comment,
        LocalDateTime createdAt) {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public BigDecimal getTotalAmount() {
        return items.stream()
                .map(item -> item.price().multiply(BigDecimal.valueOf(item.quantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
