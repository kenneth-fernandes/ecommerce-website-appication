package com.ecommerce.web.app.inventoryservice.controller;

import com.ecommerce.web.app.inventoryservice.dto.InventoryResponse;
import com.ecommerce.web.app.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
@Slf4j
public class InventoryController {
    private final InventoryService inventoryService;

    // http://localhost:8083/api/v1/inventory/iphone_13 - Old using PathVariable - {sku-code}
    // http://localhost:8083/api/v1/inventory?sku-code=iphone_13&sku-code=iphone_13_red - RequestParam
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam("sku-code") List<String> skuCodesList) {
        log.info("skuCodesList {}", skuCodesList.toString());
        return inventoryService.isInStock(skuCodesList);
    }
}
