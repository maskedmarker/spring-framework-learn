package org.example.learn.spring.framework.cycle.dep;

import org.example.learn.spring.framework.cycle.dep.service.CalculateService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.StandardEnvironment;

@ComponentScan(basePackages = {"org.example.learn.spring.framework.cycle.dep.service"})
public class CyclingDepDemo {
    public void run() {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        StandardEnvironment env = new StandardEnvironment();
        env.setActiveProfiles("Java8");
        context.setEnvironment(env);
        context.register(CyclingDepDemo.class);   // Bootstrapping @Configuration classes(启动性质的configuration class)

        context.refresh();

        //CyclingDepMain被当做ConfigurationClass注册到容器配置中心,并被实例化
        CyclingDepDemo bootstrapConfig = context.getBean(CyclingDepDemo.class);
        System.out.println("bootstrapConfig = " + bootstrapConfig);

        //配置中心会检测到作为ConfigurationClass的CalculateBootstrap被@ComponentScan注释,进而引发basePackages包被扫描
        CalculateService calService = context.getBean(CalculateService.class);
        int sum = calService.sum(1, 2, 3, 4);
        System.out.println("sum = " + sum);
        System.out.println("calService = " + calService);

    }
}
