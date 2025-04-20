package org.example.learn.spring.framework.bootstrap.init;

import org.example.learn.spring.commons.service.mall.GoodsServiceImpl;
import org.example.learn.spring.framework.bootstrap.model.config.BizConfiguration;
import org.example.learn.spring.framework.bootstrap.model.config.BizConfiguration2;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 *
 * 在被@Configuration注释的类上,@Import.value值为ImportSelector或ImportBeanDefinitionRegistrar
 *
 * ImportSelector只能控制beanDefinition的beanClass,而无法提前设置属性值(只能依赖@Value/@Autowired)
 * ImportBeanDefinitionRegistrar可以更好的控制beanDefinition(即提前为beanDefinition设置属性值)
 */
public class MyImportSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        // 只能控制beanDefinition的beanClass,而无法提前设置属性值
        return new String[]{BizConfiguration2.class.getName()};
    }
}
