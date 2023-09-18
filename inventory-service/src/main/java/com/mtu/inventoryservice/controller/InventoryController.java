package com.mtu.inventoryservice.controller;

import com.mtu.inventoryservice.dto.InventoryResponse;
import com.mtu.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam(name = "skuCode") List<String> skuCodes){
        return inventoryService.isInStock(skuCodes);
    }
}
