package org.example.learn.spring.framework.bootstrap.init;

import org.example.learn.spring.commons.service.mall.GoodsServiceImpl;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * Interface to be implemented by types that register additional bean definitions when processing @Configuration classes.
 * Along with @Configuration and ImportSelector, classes of this type may be provided to the @Import annotation (or may also be returned from an ImportSelector).
 *
 * 使用场景:
 * 在被@Configuration注释的类上,@Import.value值为ImportSelector或ImportBeanDefinitionRegistrar
 */
public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    // 因为是作用在configuration-class上,所以可以获取到configuration-class上的所有注解信息(即importingClassMetadata)
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(GoodsServiceImpl.class);
        AbstractBeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
        String beanName = "goodsService";
        String alias = "gs";
        BeanDefinitionHolder holder = new BeanDefinitionHolder(beanDefinition, beanName, new String[] { alias });
        BeanDefinitionReaderUtils.registerBeanDefinition(holder, registry);
    }
}
