package org.example.learn.spring.framework.bootstrap.config;

import org.example.learn.spring.framework.bootstrap.model.config.BizConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BasicIocConfig {

    @Bean
    public BizConfiguration bizConfiguration() {
        return new BizConfiguration();
    }
}
