package com.asejnr.notification_service.events;

import com.asejnr.notification_service.domain.NotificationService;
import com.asejnr.notification_service.domain.OrderEventEntity;
import com.asejnr.notification_service.domain.OrderEventRepository;
import com.asejnr.notification_service.domain.models.OrderCancelledEvent;
import com.asejnr.notification_service.domain.models.OrderCreatedEvent;
import com.asejnr.notification_service.domain.models.OrderDeliveredEvent;
import com.asejnr.notification_service.domain.models.OrderErrorEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
class OrderEventHandler {
    private static final Logger log = LoggerFactory.getLogger(OrderEventHandler.class);

    private final NotificationService notificationService;
    private final OrderEventRepository orderEventRepository;

    OrderEventHandler(NotificationService notificationService, OrderEventRepository orderEventRepository) {
        this.notificationService = notificationService;
        this.orderEventRepository = orderEventRepository;
    }

    @RabbitListener(queues = "${notifications.new-orders-queue}")
    void handleOrderCreatedEvent(OrderCreatedEvent event) {
        log.info("Order created event:{}", event);
        if (orderEventRepository.existsByEventId(event.eventId())) {
            log.warn("Order created event already exists:{}", event);
            return;
        }

        notificationService.sendOrderCreatedNotification(event);

        OrderEventEntity orderEvent = new OrderEventEntity(event.eventId());
        orderEventRepository.save(orderEvent);
    }

    @RabbitListener(queues = "${notifications.delivered-orders-queue}")
    void handleOrderDeliveredEvent(OrderDeliveredEvent event) {
        log.info("Order delivered event:{}", event);
        if (orderEventRepository.existsByEventId(event.eventId())) {
            log.warn("Order delivered event already exists:{}", event);
            return;
        }

        notificationService.sendOrderDeliveredNotification(event);

        OrderEventEntity orderEvent = new OrderEventEntity(event.eventId());
        orderEventRepository.save(orderEvent);
    }

    @RabbitListener(queues = "${notifications.cancelled-orders-queue}")
    void handleOrderCancelledEvent(OrderCancelledEvent event) {
        log.info("Order cancelled event:{}", event);
        if (orderEventRepository.existsByEventId(event.eventId())) {
            log.warn("Order cancelled event already exists:{}", event);
            return;
        }

        notificationService.sendOrderCancelledNotification(event);

        OrderEventEntity orderEvent = new OrderEventEntity(event.eventId());
        orderEventRepository.save(orderEvent);
    }

    @RabbitListener(queues = "${notifications.delivered-orders-queue}")
    void handleOrderDeliveredEvent(OrderErrorEvent event) {
        log.info("Order error event:{}", event);
        if (orderEventRepository.existsByEventId(event.eventId())) {
            log.warn("Order error event already exists:{}", event);
            return;
        }

        notificationService.sendOrderErrorEventNotification(event);

        OrderEventEntity orderEvent = new OrderEventEntity(event.eventId());
        orderEventRepository.save(orderEvent);
    }
}
