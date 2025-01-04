package org.example.learn.spring.hello.context.annotation;

import org.example.learn.spring.hello.context.annotation.config.InitConfig;
import org.example.learn.spring.hello.context.annotation.service.CalculateService;
import org.junit.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.StandardEnvironment;

import java.util.Arrays;

/**
 * 通过注解提供的metadata(替代xml配置文件)向spring容器提供所需bean的metadata信息
 *
 * AnnotationConfigApplicationContext默认开启的处理configuration-class的功能,
 * ClassPathXmlApplicationContext需要通过特定xml标签(比如context:component-scan)才能打开处理configuration-class的功能.
 */
public class Ch001BootstrapTest {

    /**
     * 在使用容器前,一定要先调用refresh
     */
    @Test
    public void test0() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        StandardEnvironment env = new StandardEnvironment();
        env.setActiveProfiles("Java8");
        applicationContext.setEnvironment(env);
        //
        applicationContext.register(InitConfig.class);
        // 一定要先调用refresh
        applicationContext.refresh();

        // 配置类的metadata会被放到IOC容器中,也会被实例化
        BeanDefinition beanDefinition = applicationContext.getBeanDefinition("initConfig");
        System.out.println("beanDefinition = " + beanDefinition);
        InitConfig initConfigBean = applicationContext.getBean(InitConfig.class);
        System.out.println("initConfigBean = " + initConfigBean);


        //配置中心会检测到作为ConfigurationClass的CalculateBootstrap被@ComponentScan注释,进而引发basePackages包被扫描
        CalculateService calService = applicationContext.getBean(CalculateService.class);
        int sum = calService.sum(1, 2, 3, 4);
        System.out.println("sum = " + sum);
        System.out.println("calService = " + calService);
    }


    /**
     * 最简单的启动方式
     */
    @Test
    public void test2() {
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
}
