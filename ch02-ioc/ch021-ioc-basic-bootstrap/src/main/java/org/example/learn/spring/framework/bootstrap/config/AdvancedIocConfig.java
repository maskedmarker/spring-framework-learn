package org.example.learn.spring.framework.bootstrap.config;

import org.example.learn.spring.framework.bootstrap.init.MyImportBeanDefinitionRegistrar;
import org.example.learn.spring.framework.bootstrap.init.MyImportSelector;
import org.example.learn.spring.framework.bootstrap.model.config.BizConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({MyImportBeanDefinitionRegistrar.class, MyImportSelector.class})
@Configuration
public class AdvancedIocConfig {

}
