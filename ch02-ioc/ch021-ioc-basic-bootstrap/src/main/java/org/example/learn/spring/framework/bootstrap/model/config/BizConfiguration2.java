package org.example.learn.spring.framework.bootstrap.model.config;

import org.springframework.beans.factory.annotation.Value;

public class BizConfiguration2 {

    @Value("${biz.config.opMode2}")
    private String opMode2;

    public String getOpMode2() {
        return opMode2;
    }

    public void setOpMode2(String opMode2) {
        this.opMode2 = opMode2;
    }
}
