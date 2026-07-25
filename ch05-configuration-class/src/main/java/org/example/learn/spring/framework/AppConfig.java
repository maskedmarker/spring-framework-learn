package org.example.learn.spring.framework;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public AppService appService() {
        return new AppService(appProperties());
    }

    @Bean
    public AppProperties appProperties() {
        AppProperties appProperties = new  AppProperties();
        appProperties.setAppName("hello");
        appProperties.setPort(8888);

        return appProperties;
    }
}
