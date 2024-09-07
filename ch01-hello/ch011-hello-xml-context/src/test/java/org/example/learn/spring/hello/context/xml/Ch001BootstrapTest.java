package org.example.learn.spring.hello.context.xml;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;

/**
 * 通过xml配置文件来定义bean
 */
public class Ch001BootstrapTest {

    /**
     * 最简单的启动方式
     */
    @Test
    public void test0() {
        // 构造方法中会调用refresh()方法
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:application.xml");
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        Arrays.stream(beanDefinitionNames).forEach(name -> {
            Object bean = applicationContext.getBean(name);
            System.out.println("bean = " + bean);
        });
    }

    /**
     * 在使用容器前,一定要先调用refresh
     */
    @Test
    public void test1() {
        try {
            ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext();
            applicationContext.setConfigLocations("classpath:application.xml");

            String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        } catch (Exception e) {
            System.out.println("e = " + e);
        }
    }

    /**
     * 在使用容器前,一定要先调用refresh
     */
    @Test
    public void test2() {
        try {
            ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext();
            applicationContext.setConfigLocations("classpath:application.xml");
            applicationContext.refresh();

            Arrays.stream(applicationContext.getBeanDefinitionNames()).forEach(name -> {
                Object bean = applicationContext.getBean(name);
                System.out.println("bean = " + bean);
            });
        } catch (Exception e) {
            System.out.println("e = " + e);
        }
    }
}
