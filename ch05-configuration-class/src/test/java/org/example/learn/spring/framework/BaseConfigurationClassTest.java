package org.example.learn.spring.framework;

import org.junit.After;
import org.junit.Before;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.StandardEnvironment;

/**
 * 通过最基本的方法注册bean
 */
public abstract class BaseConfigurationClassTest {

    protected AnnotationConfigApplicationContext applicationContext;


    @Before
    public void setup() {
        applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.setEnvironment(new StandardEnvironment());

        // 向容器注册bean
        registerBean(applicationContext);

        // 一定要先调用refresh
        applicationContext.refresh();
    }

    @After
    public void disposeResources() {
        applicationContext.close();
    }


    protected abstract void registerBean(AnnotationConfigApplicationContext applicationContext);
}