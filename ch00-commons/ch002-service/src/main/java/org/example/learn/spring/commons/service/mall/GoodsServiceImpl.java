package org.example.learn.spring.commons.service.mall;

import org.example.learn.spring.commons.model.mall.dto.GoodsDTO;

public class GoodsServiceImpl implements GoodsService {

    static {
        System.out.println(String.format("Class Object %s is loading", GoodsServiceImpl.class));
    }

    @Override
    public GoodsDTO getGoods(String goodsNo) {
        GoodsDTO goodsDTO = new GoodsDTO();
        goodsDTO.setGoodsNo(goodsNo);
        goodsDTO.setGoodsName("goods name");
        goodsDTO.setGoodsVendorNo("123456");

        return goodsDTO;
    }
}
