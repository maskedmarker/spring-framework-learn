package org.example.learn.spring.framework.context.xml;

import org.example.learn.spring.framework.context.xml.mall.OnlineMall;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;

/**
 * A bean definition essentially is a recipe for creating one or more objects.
 * The container looks at the recipe for a named bean when asked,
 * and uses the configuration metadata encapsulated by that bean definition to create (or acquire) an actual object.
 */
public class XmlApplicationContextDemo {

    public void runSimple() {
        System.out.printf(">>>>>>>>>>>>>  %s is running \r\n", XmlApplicationContextDemo.class);
        // 最简单的启动方式
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:application.xml");

        // 展示应用代码使用场景
        showBizUsage(applicationContext);

        // 展示spring容器的内部使用场景
        showSpringInternals(applicationContext);
    }


    public void run() {
        System.out.printf(">>>>>>>>>>>>>  %s is running \r\n", XmlApplicationContextDemo.class);

        // spring容器refresh的时候,检查是否还有bean definitions未实例化,如果有,就实例化
        // refresh the context, loading all bean definitions and creating all singletons. Alternatively, call refresh manually after further configuring the context.
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(new String[]{"classpath:application.xml"}, true, null);

        // 展示应用代码使用场景
        showBizUsage(applicationContext);

        // 展示spring容器的内部使用场景
        showSpringInternals(applicationContext);
    }

    private static void showBizUsage(ApplicationContext applicationContext) {
        OnlineMall onlineMallApp = applicationContext.getBean("onlineMall", OnlineMall.class);
        System.out.println("onlineMallApp = " + onlineMallApp);
        String msg = onlineMallApp.getOrderService().serve();
        System.out.println("msg = " + msg);
    }

    private static void showSpringInternals(ApplicationContext applicationContext) {
        // ListableBeanFactory是中性的,即应用代码可以使用,spring容器内部也是用
        // BeanDefinition只包含用户定义的bean,不包含spring内部的bean(或者这些对象不叫bean)??
        int beanDefinitionCount = applicationContext.getBeanDefinitionCount();
        System.out.println("beanDefinitionCount = " + beanDefinitionCount);
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        Arrays.asList(beanDefinitionNames).forEach(System.out::println);
        System.out.println("-----------------------------------------------------------");

        // 和BeanFactory一样,ApplicationContext是面向应用代码的
        // 和ConfigurableBeanFactory一样,ConfigurableApplicationContext也是面向spring容器内部使用.都不建议应用代码使用,除非是中间件之类的偏底层的代码.
        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) applicationContext;
        Arrays.asList(beanDefinitionNames).forEach(beanDefinitionName -> {
            BeanDefinition mergedBeanDefinition = configurableApplicationContext.getBeanFactory().getMergedBeanDefinition(beanDefinitionName);
            System.out.println("mergedBeanDefinition class name = " + mergedBeanDefinition.getClass());
            String beanClassName = mergedBeanDefinition.getBeanClassName();
            System.out.println("beanClassName = " + beanClassName);
        });
    }
}
