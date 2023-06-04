package org.example.learn.spring.framework.context.xml.mall;

import org.example.learn.spring.framework.context.xml.order.OrderService;

public class OnlineMall {

    private OrderService orderService;

    public OrderService getOrderService() {
        return orderService;
    }

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }
}
