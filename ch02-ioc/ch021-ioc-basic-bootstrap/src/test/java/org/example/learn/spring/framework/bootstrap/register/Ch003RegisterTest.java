package org.example.learn.spring.framework.bootstrap.register;

import org.example.learn.spring.commons.service.mall.GoodsService;
import org.example.learn.spring.framework.bootstrap.config.AdvancedIocConfig;
import org.example.learn.spring.framework.bootstrap.init.MyImportBeanDefinitionRegistrar;
import org.example.learn.spring.framework.bootstrap.init.MyImportSelector;
import org.example.learn.spring.framework.bootstrap.model.config.BizConfiguration;
import org.example.learn.spring.framework.bootstrap.model.config.BizConfiguration2;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.StandardEnvironment;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 通过ImportBeanDefinitionRegistrar/ImportSelector实现间接注册bean
 */
public class Ch003RegisterTest {

    private AnnotationConfigApplicationContext applicationContext;


    @Before
    public void setup() {
        applicationContext = new AnnotationConfigApplicationContext();

        // 设置environment
        StandardEnvironment env = new StandardEnvironment();
        Map<String, Object> userDefinedProp = new HashMap<>();
        userDefinedProp.put("biz.config.opMode2", "foo2");
        env.getPropertySources().addLast(new MapPropertySource("userDefinedProp", userDefinedProp));
        applicationContext.setEnvironment(env);


        // 通过ImportBeanDefinitionRegistrar/ImportSelector实现间接注册bean
        applicationContext.register(AdvancedIocConfig.class);


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
        applicationContext.getBean(MyImportBeanDefinitionRegistrar.class);
    }
    @Test(expected = NoSuchBeanDefinitionException.class)
    public void test12() {
        applicationContext.getBean(MyImportSelector.class);
    }

    /**
     * 注解@Configuration携带了@Component注解,所以被@Configuration注释的类的实例都被当作bean,会被spring容器管理
     */
    @Test
    public void test2() {
        AdvancedIocConfig iocConfig = applicationContext.getBean(AdvancedIocConfig.class);
        Assert.assertNotNull(iocConfig);
    }

    /**
     * 使用ImportBeanDefinitionRegistrar手动注册一个没有携带任何spring注解的bean
     * 并在注册beanDefinition时提前设置了属性值
     */
    @Test
    public void test3() {
        BizConfiguration bizConfiguration = applicationContext.getBean(BizConfiguration.class);
        Assert.assertNotNull(bizConfiguration);
        Assert.assertEquals("foo", bizConfiguration.getOpMode());
    }

    /**
     * 使用ImportSelector手动注册一个bean
     * 由于无法操作beanDefinition的属性值,只能使用@Value来通过容器为其注入属性值
     */
    @Test
    public void test32() {
        BizConfiguration2 bizConfiguration = applicationContext.getBean(BizConfiguration2.class);
        Assert.assertNotNull(bizConfiguration);
        Assert.assertEquals("foo2", bizConfiguration.getOpMode2());
    }
}