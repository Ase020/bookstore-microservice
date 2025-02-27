package com.asejnr.order_service.jobs;

import com.asejnr.order_service.domain.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
class OrderProcessingJob {
    private static final Logger log = LoggerFactory.getLogger(OrderProcessingJob.class);

    private final OrderService orderService;

    OrderProcessingJob(OrderService orderService) {
        this.orderService = orderService;
    }

    @Scheduled(cron = "${orders.new-order-events-cron-job}")
    public void processNewOrders() {
        log.info("Processing new orders at {}", LocalDateTime.now());
        orderService.processNewOrders();
    }
}
