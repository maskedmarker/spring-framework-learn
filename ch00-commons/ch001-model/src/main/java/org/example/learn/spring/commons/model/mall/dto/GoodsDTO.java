package org.example.learn.spring.commons.model.mall.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.math.BigDecimal;

public class GoodsDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    // 商品编号
    private String goodsNo;

    // 商品名称
    private String goodsName;

    // 生产厂家编号
    private String goodsVendorNo;

    public String getGoodsNo() {
        return goodsNo;
    }

    public void setGoodsNo(String goodsNo) {
        this.goodsNo = goodsNo;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsVendorNo() {
        return goodsVendorNo;
    }

    public void setGoodsVendorNo(String goodsVendorNo) {
        this.goodsVendorNo = goodsVendorNo;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("goodsNo", goodsNo)
                .append("goodsName", goodsName)
                .append("goodsVendorNo", goodsVendorNo)
                .toString();
    }
}
