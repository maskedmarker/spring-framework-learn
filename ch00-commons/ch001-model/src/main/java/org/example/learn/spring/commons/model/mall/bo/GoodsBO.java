package org.example.learn.spring.commons.model.mall.bo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.example.learn.spring.commons.model.mall.dto.GoodsVendorDTO;

import java.io.Serializable;

public class GoodsBO implements Serializable {

    private static final long serialVersionUID = 1L;

    // 商品编号
    private String goodsNo;

    // 商品名称
    private String goodsName;

    // 生产厂家
    private GoodsVendorDTO goodsVendorDTO;

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

    public GoodsVendorDTO getGoodsVendorDTO() {
        return goodsVendorDTO;
    }

    public void setGoodsVendorDTO(GoodsVendorDTO goodsVendorDTO) {
        this.goodsVendorDTO = goodsVendorDTO;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("goodsNo", goodsNo)
                .append("goodsName", goodsName)
                .append("goodsVendorDTO", goodsVendorDTO)
                .toString();
    }
}
