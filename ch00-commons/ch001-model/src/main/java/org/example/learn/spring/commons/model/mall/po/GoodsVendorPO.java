package org.example.learn.spring.commons.model.mall.po;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class GoodsVendorPO implements Serializable {

    private static final long serialVersionUID = 1L;

    // 生产厂家编号
    private String vendorNo;

    // 生产厂商名称
    private String vendorName;

    public String getVendorNo() {
        return vendorNo;
    }

    public void setVendorNo(String vendorNo) {
        this.vendorNo = vendorNo;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("vendorNo", vendorNo)
                .append("vendorName", vendorName)
                .toString();
    }
}
