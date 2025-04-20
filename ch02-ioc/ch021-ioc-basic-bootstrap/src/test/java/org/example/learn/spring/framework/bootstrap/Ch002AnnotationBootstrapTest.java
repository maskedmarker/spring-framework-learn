package org.example.learn.spring.framework.bootstrap;

import org.example.learn.spring.framework.bootstrap.config.BasicIocConfig;
import org.example.learn.spring.framework.bootstrap.model.config.BizConfiguration;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.StandardEnvironment;

import java.util.Arrays;

/**
 * 通过Configuration-class实现间接注册bean
 */
public class Ch002AnnotationBootstrapTest {

    private AnnotationConfigApplicationContext applicationContext;


    @Before
    public void setup() {
        applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.setEnvironment(new StandardEnvironment());


        // 向容器注册一个configuration-class,间接注册了bean
        applicationContext.register(BasicIocConfig.class);


        // 一定要先调用refresh
        applicationContext.refresh();
    }

    @After
    public void disposeResources() {
        applicationContext.close();
    }

    @Test
    public void test0() {
        Class<BizConfiguration> beanType = BizConfiguration.class;
        BizConfiguration goodsService = applicationContext.getBean(beanType);
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
}