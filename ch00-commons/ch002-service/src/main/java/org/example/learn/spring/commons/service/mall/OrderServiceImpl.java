package org.example.learn.spring.commons.service.mall;

import org.example.learn.spring.commons.model.mall.dto.OrderDTO;

public class OrderServiceImpl implements OrderService {

    static {
        System.out.println(String.format("Class Object %s is loading", OrderServiceImpl.class));
    }

    private GoodsService goodsService;

    public GoodsService getGoodsService() {
        return goodsService;
    }

    public void setGoodsService(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    @Override
    public void submitOrder(OrderDTO orderDTO) {
        System.out.println("submitting order " + orderDTO);
    }
}
