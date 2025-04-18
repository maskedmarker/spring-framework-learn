package org.example.learn.spring.framework.bootstrap;

import org.example.learn.spring.commons.service.mall.GoodsService;
import org.example.learn.spring.framework.bootstrap.config.IocConfig;
import org.example.learn.spring.framework.bootstrap.init.MyImportBeanDefinitionRegistrar;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.StandardEnvironment;

import java.util.Arrays;

public class Ch001AnnotationBootstrapTest {

    private AnnotationConfigApplicationContext applicationContext;


    @Before
    public void setup() {
        applicationContext = new AnnotationConfigApplicationContext();
        StandardEnvironment env = new StandardEnvironment();
        applicationContext.setEnvironment(env);
        //
        applicationContext.register(IocConfig.class);
        // 一定要先调用refresh
        applicationContext.refresh();
    }

    @After
    public void disposeResources() {
        applicationContext.close();
    }

    @Test
    public void test0() {
        Class<GoodsService> beanType = GoodsService.class;
        GoodsService goodsService = applicationContext.getBean(beanType);
        Assert.assertNotNull(goodsService);

        String[] namesForType = applicationContext.getBeanNamesForType(beanType);
        Arrays.stream(namesForType).forEach(name -> {
            System.out.println("namesForType = " + name);
            String[] alias = applicationContext.getAliases(name);
            Assert.assertNotNull(alias);
            Arrays.stream(namesForType).forEach(i -> {
                System.out.println(String.format("alias of %s = %s", name, i));
            });
        });
    }

    /**
     * ImportBeanDefinitionRegistrar/ImportSelector是不会被注册到容器中的.
     */
    @Test(expected = NoSuchBeanDefinitionException.class)
    public void test1() {
        MyImportBeanDefinitionRegistrar myImportBeanDefinitionRegistrar = applicationContext.getBean(MyImportBeanDefinitionRegistrar.class);
    }
}