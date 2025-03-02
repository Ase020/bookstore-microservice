package com.asejnr.notification_service.events;

import com.asejnr.notification_service.domain.models.OrderCreatedEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
class OrderEventHandler {

    @RabbitListener(queues = "${notifications.new-orders-queue}")
    void handleOrderCreatedEvent(OrderCreatedEvent event) {
        System.out.println("Order created event:" + event);
    }
}
