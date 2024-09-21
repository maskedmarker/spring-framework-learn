package org.example.learn.spring.hello.context.xml;

import org.junit.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;

/**
 * 和BeanFactory一样,ApplicationContext是面向应用代码的
 * BeanFactory仅仅提供了最基本的getBean功能;
 * ApplicationContext还支持查看bean definitions
 * ConfigurableApplicationContext支持BeanFactoryPostProcessor(Configuration and lifecycle methods are encapsulated here to avoid making them obvious to ApplicationContext client code)
 *
 */
public class Ch002ApplicationContextTest {

    /**
     *
     */
    @Test
    public void test0() {
        // 构造方法中会调用refresh()方法
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:application.xml");

        // ListableBeanFactory是中性的,即应用代码可以使用,spring容器内部也是用
        // BeanDefinition只包含用户定义的bean,不包含spring内部的bean(或者这些对象不叫bean)??
        int beanDefinitionCount = applicationContext.getBeanDefinitionCount();
        System.out.println("beanDefinitionCount = " + beanDefinitionCount);
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        Arrays.asList(beanDefinitionNames).forEach(System.out::println);
        System.out.println("-----------------------------------------------------------");

        // 和BeanFactory一样,ApplicationContext是面向应用代码的,但是ApplicationContext
        // 和ConfigurableBeanFactory一样,ConfigurableApplicationContext也是面向spring容器内部使用.都不建议应用代码使用,除非是中间件之类的偏底层的代码.
        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) applicationContext;
        ConfigurableListableBeanFactory beanFactory = configurableApplicationContext.getBeanFactory();
        Arrays.asList(beanDefinitionNames).forEach(beanDefinitionName -> {
            BeanDefinition mergedBeanDefinition = beanFactory.getMergedBeanDefinition(beanDefinitionName);
            System.out.println("mergedBeanDefinition class name = " + mergedBeanDefinition.getClass());
            String beanClassName = mergedBeanDefinition.getBeanClassName();
            System.out.println("beanClassName = " + beanClassName);
        });
    }

}
