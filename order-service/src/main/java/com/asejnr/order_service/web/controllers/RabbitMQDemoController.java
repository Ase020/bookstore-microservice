package com.asejnr.order_service.web.controllers;

import com.asejnr.order_service.ApplicationProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class RabbitMQDemoController {
    private final RabbitTemplate rabbitTemplate;
    private final ApplicationProperties properties;

    RabbitMQDemoController(RabbitTemplate rabbitTemplate, ApplicationProperties properties) {
        this.rabbitTemplate = rabbitTemplate;
        this.properties = properties;
    }

    @PostMapping("/send")
    public void sendMessage(@RequestBody MyMessage myMessage) {
        rabbitTemplate.convertAndSend(properties.orderEventsExchange(), myMessage.routingKey(), myMessage.payload());
    }
}

record MyMessage(String routingKey, MyPayload payload) {}

record MyPayload(String content) {}
