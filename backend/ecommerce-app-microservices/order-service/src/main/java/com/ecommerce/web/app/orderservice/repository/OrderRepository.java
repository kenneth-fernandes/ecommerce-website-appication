package com.ecommerce.web.app.orderservice.repository;

import com.ecommerce.web.app.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
