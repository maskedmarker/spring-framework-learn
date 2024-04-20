package org.example.learn.spring.framework.aop.aspectj;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 使用aspectj的2中方式
 *
 * To enable @AspectJ support with Java @Configuration, add the @EnableAspectJAutoProxy annotation
 * To enable @AspectJ support with XML-based configuration, use the aop:aspectj-autoproxy element
 */
@Configuration
@EnableAspectJAutoProxy
public class AopConfig {

}
