package org.example.learn.spring.framework;

import java.util.StringJoiner;

public class AppProperties {

    private String appName;

    private int port;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AppProperties.class.getSimpleName() + "[", "]")
                .add("appName='" + appName + "'")
                .add("port=" + port)
                .toString();
    }
}
