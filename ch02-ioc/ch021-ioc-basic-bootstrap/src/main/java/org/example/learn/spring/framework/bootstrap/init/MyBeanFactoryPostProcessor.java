package org.example.learn.spring.framework.bootstrap.init;

import org.example.learn.spring.framework.bootstrap.model.config.BizConfiguration3;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

/**
 * 除了通过@Component来静态声明bean之外, ImportSelector或ImportBeanDefinitionRegistrar提供了动态声明bean
 * BeanDefinitionRegistryPostProcessor也是可以认做是一种动态声明bean的扩展点,只不过会晚于前面3者
 */
public class MyBeanFactoryPostProcessor implements BeanDefinitionRegistryPostProcessor {


    /**
     * 这个方法是用来新增beanDefinition
     */
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(BizConfiguration3.class);
        beanDefinitionBuilder.addPropertyValue("opMode3", "foo3");
        AbstractBeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
        String beanName = "bizConfiguration3";
        BeanDefinitionHolder holder = new BeanDefinitionHolder(beanDefinition, beanName);
        BeanDefinitionReaderUtils.registerBeanDefinition(holder, registry);
    }

    /**
     * 这个是用来 overriding or adding properties beanDefinition的属性的
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinition beanDefinition = beanFactory.getBeanDefinition("bizConfiguration3");

        Object oldValue = beanDefinition.getPropertyValues().getPropertyValue("opMode3").getValue();
        String newValue = oldValue + "--haha";
        // 使用新的属性值
        beanDefinition.getPropertyValues().add("opMode3", newValue);
    }
}
