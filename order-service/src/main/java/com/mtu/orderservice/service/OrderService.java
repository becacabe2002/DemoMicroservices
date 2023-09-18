package com.mtu.orderservice.service;

import com.mtu.orderservice.dto.InventoryResponse;
import com.mtu.orderservice.dto.OrderLineItemsDto;
import com.mtu.orderservice.dto.OrderRequest;
import com.mtu.orderservice.model.Order;
import com.mtu.orderservice.model.OrderLineItems;
import com.mtu.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
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

    private final WebClient webClient;

    public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> listOrderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(OrderLineItemsDto::toOrderLineItems).toList();

        order.setOrderLineItemsList(listOrderLineItems);

        List<String> skuCodes = listOrderLineItems.stream().map(OrderLineItems::getSkuCode).toList();

        // Need to check if items are available or not before save order
        // Synchronous communication
        InventoryResponse[] inventoryResponses =
                webClient.get()
                        .uri("http://localhost:8082/api/inventory",
                                uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                        .retrieve()
                        .bodyToMono(InventoryResponse[].class)
                        .block();
        // asynchronous by default -> have to block to make it synchronous

        if(inventoryResponses.length == 0){
            throw new IllegalArgumentException("Invalid skuCode!!!");
        }

        boolean checkRes = Arrays.stream(inventoryResponses).allMatch(InventoryResponse::isInStock);

        if(checkRes){
            orderRepository.save(order);
        } else{
            throw new IllegalArgumentException("Product is not in stock!!!");
        }
    }
}
