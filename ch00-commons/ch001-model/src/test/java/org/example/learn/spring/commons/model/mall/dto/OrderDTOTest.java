package org.example.learn.spring.commons.model.mall.dto;

import org.junit.Test;

import java.math.BigDecimal;

public class OrderDTOTest {

    @Test
    public void test0() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderNo("123456789");
        orderDTO.setGoodsNo("555");
        orderDTO.setAmount(100);
        orderDTO.setPrice(new BigDecimal("9.99"));
        System.out.println("orderDTO = " + orderDTO);
    }

}