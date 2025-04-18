package org.example.learn.spring.framework.bootstrap.config;

import org.example.learn.spring.framework.bootstrap.init.MyImportBeanDefinitionRegistrar;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import(MyImportBeanDefinitionRegistrar.class)
@Configuration
public class IocConfig {

}
