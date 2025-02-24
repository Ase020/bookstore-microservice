package com.asejnr.order_service.web.controllers;

import com.asejnr.order_service.domain.OrderService;
import com.asejnr.order_service.domain.SecurityService;
import com.asejnr.order_service.domain.model.CreateOrderRequest;
import com.asejnr.order_service.domain.model.CreateOrderResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;
    private final SecurityService securityService;

    OrderController(OrderService orderService, SecurityService securityService) {
        this.orderService = orderService;
        this.securityService = securityService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CreateOrderResponse createOrder(@Valid @RequestBody CreateOrderRequest request) {
        String username = securityService.getLoginUsername();
        logger.info("Creating order for {}", username);
        return orderService.createOrder(username, request);
    }
}
