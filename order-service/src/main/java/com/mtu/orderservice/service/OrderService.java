package com.mtu.orderservice.service;

import com.mtu.orderservice.dto.OrderLineItemsDto;
import com.mtu.orderservice.dto.OrderRequest;
import com.mtu.orderservice.model.Order;
import com.mtu.orderservice.model.OrderLineItems;
import com.mtu.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

    public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> listOrderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(OrderLineItemsDto::toOrderLineItems).toList();

        order.setOrderLineItemsList(listOrderLineItems);

        orderRepository.save(order);
    }
}
