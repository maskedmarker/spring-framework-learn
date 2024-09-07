package org.example.learn.spring.commons.model.mall.bo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.example.learn.spring.commons.model.mall.dto.GoodsDTO;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderBO implements Serializable {

    private static final long serialVersionUID = 1L;

    // 订单编号
    private String orderNo;

    // 商品编号
    private GoodsDTO goods;

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

    public GoodsDTO getGoods() {
        return goods;
    }

    public void setGoods(GoodsDTO goods) {
        this.goods = goods;
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
        return new ToStringBuilder(this)
                .append("orderNo", orderNo)
                .append("goods", goods)
                .append("amount", amount)
                .append("price", price)
                .toString();
    }
}
