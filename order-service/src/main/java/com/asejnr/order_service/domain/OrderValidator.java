package com.asejnr.order_service.domain;

import com.asejnr.order_service.clients.catalog.Product;
import com.asejnr.order_service.clients.catalog.ProductServiceClient;
import com.asejnr.order_service.domain.model.CreateOrderRequest;
import com.asejnr.order_service.domain.model.OrderItem;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
class OrderValidator {
    private static final Logger logger = LoggerFactory.getLogger(OrderValidator.class);

    private final ProductServiceClient productServiceClient;

    OrderValidator(ProductServiceClient productServiceClient) {
        this.productServiceClient = productServiceClient;
    }

    void validateOrder(CreateOrderRequest orderRequest) throws InvalidOrderException {
        Set<OrderItem> items = orderRequest.items();
        for (OrderItem item : items) {
            Product product = productServiceClient
                    .getProductByCode(item.code())
                    .orElseThrow(() -> new InvalidOrderException("Invalid product code: " + item.code()));
            if (item.price().compareTo(product.price()) != 0) {
                logger.error("Product price not matching. Actual: {}, Received: {}", product.price(), item.price());
                throw new InvalidOrderException("Product price not matching. Expected: " +  product.price() + ", Received: " + item.price());
            }
        }
    }
}
