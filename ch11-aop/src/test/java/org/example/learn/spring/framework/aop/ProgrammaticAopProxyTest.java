package org.example.learn.spring.framework.aop;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.example.learn.spring.framework.model.Pojo;
import org.example.learn.spring.framework.model.SimplePojo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.aop.SpringProxy;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.framework.adapter.UnknownAdviceTypeException;

import java.util.Arrays;

/**
 * spring-aop是通过proxy的方式来实现的.
 * 用户调用的对象实际上是proxy对象,而非原对象(即advised object)
 *
 * Creating AOP Proxies Programmatically with the ProxyFactory
 * Factory for AOP proxies for programmatic use, rather than via declarative setup in a bean factory.
 * 以编程的方式使用AOP,而非声明式
 */
public class ProgrammaticAopProxyTest {

    private static class MyMethodInterceptor implements MethodInterceptor {

        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            System.out.println("in MyMethodInterceptor: " + "invocation = " + invocation);
            return invocation.proceed();
        }
    }

    private static class MyAdvice implements Advice {

        public Object invoke(MethodInvocation invocation) throws Throwable {
            System.out.println("in MyAdvice: " + "invocation = " + invocation);
            return invocation.proceed();
        }
    }

    @Test
    public void test0() {
        /* 步骤1. 构造定制化的AOP工厂类实例 */

        // AOP框架会自动探测入参实现的interface
        ProxyFactory factory = new ProxyFactory(new SimplePojo());

        // 可以省略,除非想新增interface
        // factory.addInterface(Pojo.class);

        // 添加切面逻辑的实现类
         factory.addAdvice(new MyMethodInterceptor());

        // Set whether the proxy should be exposed by the AOP framework as a ThreadLocal for retrieval via the AopContext class.(是否要将proxy暴漏在threadLocal中,以供他用)
        factory.setExposeProxy(true);

        /* 步骤2. 通过AOP工厂类生成代理类对象 */
        // AOP factory的配置已完成,可以生成proxy对象了
        Pojo proxy = (Pojo) factory.getProxy();

        /* 步骤3. 使用代理类 */
        // this is a method call on the proxy!
        proxy.foo();

        // 打印proxy对象的类型.
        System.out.println("proxy.getClass() = " + proxy.getClass());
        // 默认情况下,spring-aop使用的是jdk proxy
        Assert.assertTrue(proxy.getClass().getName().startsWith("com.sun.proxy.$Proxy"));

        Arrays.stream(proxy.getClass().getInterfaces()).forEach(System.out::println);
        // proxy除了实现用户的interface,还会实现spring内部的一些interface(方便spring识别该bean是代理类)
        Assert.assertTrue(proxy instanceof Pojo);
        Assert.assertTrue(proxy instanceof Advised);
        Assert.assertTrue(proxy instanceof SpringProxy);
    }

    /**
     * org.springframework.aop.framework.adapter.UnknownAdviceTypeException:
     * Advice object [org.example.learn.spring.framework.aop.ProgrammaticAOPProxyTest$MyAdvice@22a71081] is neither a supported subinterface of [org.aopalliance.aop.Advice] nor an [org.springframework.aop.Advisor]
     *
     * spring的AOP框架只支持Advice的子类MethodInterceptor
     */
    @Test(expected = UnknownAdviceTypeException.class)
    public void test1() {
        ProxyFactory factory = new ProxyFactory(new SimplePojo());
        factory.addAdvice(new MyAdvice());
        factory.setExposeProxy(true);
        Pojo proxy = (Pojo) factory.getProxy();
        proxy.foo();
    }
}
