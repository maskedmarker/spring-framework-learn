package org.example.learn.spring.framework.bootstrap.register;

import org.example.learn.spring.framework.bootstrap.init.MyBeanFactoryPostProcessor;
import org.example.learn.spring.framework.bootstrap.model.config.BizConfiguration3;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.StandardEnvironment;

/**
 * 通过BeanFactoryPostProcessor实现间接注册bean
 */
public class Ch004RegisterTest {

    private AnnotationConfigApplicationContext applicationContext;


    @Before
    public void setup() {
        applicationContext = new AnnotationConfigApplicationContext();

        // 设置environment
        StandardEnvironment env = new StandardEnvironment();
        applicationContext.setEnvironment(env);


        // 通过BeanFactoryPostProcessor实现间接注册bean
        applicationContext.register(MyBeanFactoryPostProcessor.class);


        // 一定要先调用refresh
        applicationContext.refresh();
    }

    @After
    public void disposeResources() {
        applicationContext.close();
    }

    @Test
    public void test0() {
        BizConfiguration3 bizConfiguration = applicationContext.getBean(BizConfiguration3.class);
        Assert.assertNotNull(bizConfiguration);
        Assert.assertEquals("foo3--haha", bizConfiguration.getOpMode3());
    }

    @Test
    public void test1() {
        MyBeanFactoryPostProcessor bean = applicationContext.getBean(MyBeanFactoryPostProcessor.class);
        Assert.assertNotNull(bean);
    }
}