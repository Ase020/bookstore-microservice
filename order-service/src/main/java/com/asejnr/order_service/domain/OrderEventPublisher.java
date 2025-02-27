package com.asejnr.order_service.domain;

import com.asejnr.order_service.ApplicationProperties;
import com.asejnr.order_service.domain.model.OrderCancelledEvent;
import com.asejnr.order_service.domain.model.OrderCreatedEvent;
import com.asejnr.order_service.domain.model.OrderDeliveredEvent;
import com.asejnr.order_service.domain.model.OrderErrorEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
class OrderEventPublisher {
    private final ApplicationProperties applicationProperties;
    private final RabbitTemplate rabbitTemplate;

    OrderEventPublisher(ApplicationProperties applicationProperties, RabbitTemplate rabbitTemplate) {
        this.applicationProperties = applicationProperties;
        this.rabbitTemplate = rabbitTemplate;
    }


    public void publish(OrderCreatedEvent orderCreatedEvent) {
        this.send(applicationProperties.newOrdersQueue(), orderCreatedEvent);
    }

    public void publish(OrderDeliveredEvent orderDeliveredEvent) {
        this.send(applicationProperties.deliveredOrdersQueue(), orderDeliveredEvent);
    }

    public void publish(OrderCancelledEvent orderCancelledEvent) {
        this.send(applicationProperties.cancelledOrdersQueue(), orderCancelledEvent);
    }

    public void publish(OrderErrorEvent orderErrorEvent) {
        this.send(applicationProperties.errorOrdersQueue(), orderErrorEvent);
    }

    private void send(String routingKey, Object payload) {
        rabbitTemplate.convertAndSend(applicationProperties.orderEventsExchange(), routingKey, payload);
    }
}
