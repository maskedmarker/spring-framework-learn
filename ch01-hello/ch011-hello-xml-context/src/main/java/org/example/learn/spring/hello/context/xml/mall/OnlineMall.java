package org.example.learn.spring.hello.context.xml.mall;

import org.example.learn.spring.hello.context.xml.order.OrderService;

public class OnlineMall {

    private OrderService orderService;

    public OrderService getOrderService() {
        return orderService;
    }

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }
}
