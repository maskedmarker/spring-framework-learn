package org.example.learn.spring.framework;

public class AppService {

    private final AppProperties appProperties;

    public AppService(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    public void processBiz() {
        System.out.printf("%s is processing biz at port %s%n", appProperties.getAppName(), appProperties.getPort());
    }
}
