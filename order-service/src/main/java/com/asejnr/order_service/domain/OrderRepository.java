package com.asejnr.order_service.domain;

import com.asejnr.order_service.domain.model.OrderStatus;
import com.asejnr.order_service.domain.model.OrderSummary;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByStatus(OrderStatus orderStatus);

    Optional<OrderEntity> findByOrderNumber(String orderNumber);

    default void updateOrderStatus(String orderNumber, OrderStatus orderStatus) {
        OrderEntity order = this.findByOrderNumber(orderNumber).orElseThrow();
        order.setStatus(orderStatus);
        this.save(order);
    }

    @Query(
            """
        SELECT NEW com.asejnr.order_service.domain.model.OrderSummary(order.orderNumber, order.status)
        FROM OrderEntity order
        WHERE order.userName  = :username
        """)
    List<OrderSummary> findByUserName(String username);

    @Query(
            """
         SELECT DISTINCT ord
         FROM OrderEntity ord LEFT JOIN FETCH ord.items
         WHERE ord.userName = :username AND ord.orderNumber = :orderNumber
         """)
    Optional<OrderEntity> findByUserNameAndOrderNumber(String username, String orderNumber);
}
