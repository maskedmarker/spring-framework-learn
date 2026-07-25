package org.example.learn.spring.framework;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 通过最基本的方法注册bean
 */
public class Ch001ConfigurationClassProxyTest extends BaseConfigurationClassTest{


    @Override
    protected void registerBean(AnnotationConfigApplicationContext applicationContext) {
        applicationContext.registerBean(AppConfig.class);
    }

    @Test
    public void test0() {
        AppConfig appConfig = applicationContext.getBean(AppConfig.class);
        System.out.println("appConfig = " + appConfig);
        Assert.assertSame(AppConfig.class, appConfig.getClass().getSuperclass());
    }

    @Test
    public void test1() {
        String workingDir = System.getProperty("user.dir");
        System.out.println("workingDir = " + workingDir);
    }
}