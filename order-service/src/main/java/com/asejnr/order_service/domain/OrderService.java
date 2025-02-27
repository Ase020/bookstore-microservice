package com.asejnr.order_service.domain;

import com.asejnr.order_service.domain.model.CreateOrderRequest;
import com.asejnr.order_service.domain.model.CreateOrderResponse;
import com.asejnr.order_service.domain.model.OrderCreatedEvent;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;
    private final OrderValidator orderValidator;
    private final OrderEventService orderEventService;

    OrderService(OrderRepository orderRepository, OrderValidator orderValidator, OrderEventService orderEventService) {

        this.orderRepository = orderRepository;
        this.orderValidator = orderValidator;
        this.orderEventService = orderEventService;
    }

    public CreateOrderResponse createOrder(String username, CreateOrderRequest request) throws InvalidOrderException {
        orderValidator.validateOrder(request);
        OrderEntity newOrder = OrderMapper.convertOrderToEntity(request);
        newOrder.setUserName(username);

        OrderEntity savedOrder = this.orderRepository.save(newOrder);
        logger.info("Order created: {} orderNumber: {}", savedOrder, savedOrder.getOrderNumber());

        OrderCreatedEvent orderCreatedEvent = OrderEventMapper.buildOrderCreatedEvent(savedOrder);
        orderEventService.save(orderCreatedEvent);

        return new CreateOrderResponse(savedOrder.getOrderNumber());
    }
}
