package com.asejnr.order_service.domain;

import com.asejnr.order_service.domain.model.CreateOrderRequest;
import com.asejnr.order_service.domain.model.CreateOrderResponse;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;

    OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public CreateOrderResponse createOrder(String username, CreateOrderRequest request) {
        OrderEntity newOrder = OrderMapper.convertOrderToEntity(request);
        newOrder.setUserName(username);

        OrderEntity savedOrder = this.orderRepository.save(newOrder);
        logger.info("Order created: {} orderNumber: {}", savedOrder, savedOrder.getOrderNumber());
        return new CreateOrderResponse(savedOrder.getOrderNumber());
    }
}
