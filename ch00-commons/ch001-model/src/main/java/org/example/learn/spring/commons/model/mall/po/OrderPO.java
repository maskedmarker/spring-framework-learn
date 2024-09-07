package org.example.learn.spring.commons.model.mall.po;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderPO implements Serializable {

    private static final long serialVersionUID = 1L;

    // 订单编号
    private String orderNo;

    // 商品编号
    private String goodsNo;

    // 商品数量
    private Integer amount;

    // 商品单价
    private BigDecimal price;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getGoodsNo() {
        return goodsNo;
    }

    public void setGoodsNo(String goodsNo) {
        this.goodsNo = goodsNo;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return new org.apache.commons.lang3.builder.ToStringBuilder(this)
                .append("orderNo", orderNo)
                .append("goodsNo", goodsNo)
                .append("amount", amount)
                .append("price", price)
                .toString();
    }
}
