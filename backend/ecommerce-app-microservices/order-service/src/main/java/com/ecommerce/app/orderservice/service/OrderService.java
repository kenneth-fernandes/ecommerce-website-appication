package com.ecommerce.app.orderservice.service;

import com.ecommerce.app.orderservice.dto.InventoryResponse;
import com.ecommerce.app.orderservice.dto.OrderLineItemsDto;
import com.ecommerce.app.orderservice.dto.OrderRequest;
import com.ecommerce.app.orderservice.event.OrderPlacedEvent;
import com.ecommerce.app.orderservice.model.Order;
import com.ecommerce.app.orderservice.model.OrderLineItems;
import com.ecommerce.app.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    public String placeOrder (OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        
        List<OrderLineItems> orderLineItemsList = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();
        order.setOrderLineItemsList(orderLineItemsList);

        List<String> skuCodesList = orderLineItemsList.stream().map(OrderLineItems::getSkuCode).toList();

        // Check if inventory is present by calling the Inventory Service - WebClient
        InventoryResponse[] inventoryResponsesArr = webClientBuilder.build().get()
                .uri("http://inventory-service/api/v1/inventory",
                        uriBuilder -> uriBuilder.queryParam("sku-code", skuCodesList).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block(); // Block will do synchronous request to API

        boolean allProductsInStock = Arrays.stream(inventoryResponsesArr).allMatch(InventoryResponse::isInStock);

        if(allProductsInStock) {
            orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("Product is not in stock, please try again later");
        }
        kafkaTemplate.send("notificationTopic", new OrderPlacedEvent(order.getOrderNumber()));
        return "Order placed successfully";
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());

        return orderLineItems;
    }
}
