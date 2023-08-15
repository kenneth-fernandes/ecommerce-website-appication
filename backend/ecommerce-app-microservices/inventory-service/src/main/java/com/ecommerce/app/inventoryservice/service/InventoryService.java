package com.ecommerce.app.inventoryservice.service;

import com.ecommerce.app.inventoryservice.model.Inventory;
import com.ecommerce.app.inventoryservice.dto.InventoryResponse;
import com.ecommerce.app.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    //@SneakyThrows
    public List<InventoryResponse> isInStock(List<String> skuCodesList) {
//        log.info("Wait Started");
//        Thread.sleep(10000);
//        log.info("Wait ended");
        List<Inventory> bySkuCodeIn = inventoryRepository.findBySkuCodeIn(skuCodesList);
        return bySkuCodeIn
                .stream()
                .map(inventory -> InventoryResponse.builder()
                        .skuCode(inventory.getSkuCode())
                        .isInStock(inventory.getQuantity() > 0)
                        .build()).toList();
    }
}
