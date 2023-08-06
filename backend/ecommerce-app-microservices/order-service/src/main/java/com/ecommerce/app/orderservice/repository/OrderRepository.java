package com.ecommerce.app.orderservice.repository;

import com.ecommerce.app.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
