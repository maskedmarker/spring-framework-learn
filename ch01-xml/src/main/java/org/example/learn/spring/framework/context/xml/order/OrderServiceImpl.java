package org.example.learn.spring.framework.context.xml.order;

public class OrderServiceImpl implements OrderService {

    static {
        System.out.println(String.format("Class Object %s is loading", OrderServiceImpl.class));
    }

    @Override
    public String serve() {
        return "Hello World";
    }
}
