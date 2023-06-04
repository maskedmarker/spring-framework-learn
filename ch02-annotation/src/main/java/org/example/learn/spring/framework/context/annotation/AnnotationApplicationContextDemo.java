package org.example.learn.spring.framework.context.annotation;

import org.example.learn.spring.framework.context.annotation.service.CalculateService;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.stereotype.Component;

@ComponentScan(basePackages = {"org.example.learn.spring.framework.context.annotation.service"})
public class AnnotationApplicationContextDemo {

    public void run() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        StandardEnvironment env = new StandardEnvironment();
        env.setActiveProfiles("Java8");
        context.setEnvironment(env);
        // 将AnnotationApplicationContextDemo作为配置类,等价于添加了@Configuration,从而触发@ComponentScan
        context.register(AnnotationApplicationContextDemo.class);

        context.refresh();


        // 配置类的metadata会被放到IOC容器中,也会被实例化
        BeanDefinition beanDefinition = context.getBeanDefinition("annotationApplicationContextDemo");
        System.out.println("beanDefinition = " + beanDefinition);
        AnnotationApplicationContextDemo rootConfigClazzInstance = context.getBean(AnnotationApplicationContextDemo.class);
        System.out.println("rootConfigClazzInstance = " + rootConfigClazzInstance);


        //配置中心会检测到作为ConfigurationClass的CalculateBootstrap被@ComponentScan注释,进而引发basePackages包被扫描
        CalculateService calService = context.getBean(CalculateService.class);
        int sum = calService.sum(1, 2, 3, 4);
        System.out.println("sum = " + sum);
        System.out.println("calService = " + calService);
    }
}
