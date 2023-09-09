package com.mtu.orderservice.dto;

import com.mtu.orderservice.model.OrderLineItems;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderLineItemsDto {
//    private Long id; // generated value
    private String skuCode;
    private BigDecimal price;
    private Integer quantity;

    public OrderLineItems toOrderLineItems(){
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setSkuCode(this.skuCode);
        orderLineItems.setPrice(this.price);
        orderLineItems.setQuantity(this.quantity);
        return orderLineItems;
    }
}
