package com.asejnr.order_service.web.controllers;

import com.asejnr.order_service.domain.InvalidOrderException;
import com.asejnr.order_service.domain.OrderNotFoundException;
import com.asejnr.order_service.domain.OrderService;
import com.asejnr.order_service.domain.SecurityService;
import com.asejnr.order_service.domain.model.CreateOrderRequest;
import com.asejnr.order_service.domain.model.CreateOrderResponse;
import com.asejnr.order_service.domain.model.OrderDTO;
import com.asejnr.order_service.domain.model.OrderSummary;
import jakarta.validation.Valid;
import java.util.List;
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
    CreateOrderResponse createOrder(@Valid @RequestBody CreateOrderRequest request) throws InvalidOrderException {
        String username = securityService.getLoginUsername();
        logger.info("Creating order for {}", username);
        return orderService.createOrder(username, request);
    }

    @GetMapping
    List<OrderSummary> getOrders() {
        String username = securityService.getLoginUsername();
        logger.info("Retrieving orders for user: {}", username);
        return orderService.findOrders(username);
    }

    @GetMapping(value = "/{orderNumber}")
    OrderDTO getOrder(@PathVariable(value = "orderNumber") String orderNumber) throws OrderNotFoundException {
        logger.info("Retrieving order for {}", orderNumber);
        String username = securityService.getLoginUsername();
        return orderService
                .findUserOrder(username, orderNumber)
                .orElseThrow(() -> new OrderNotFoundException(orderNumber));
    }
}
