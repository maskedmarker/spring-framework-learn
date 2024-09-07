package org.example.learn.spring.commons.service.mall;

import org.example.learn.spring.commons.model.mall.dto.OrderDTO;

public interface OrderService {

    void submitOrder(OrderDTO orderDTO);
}
