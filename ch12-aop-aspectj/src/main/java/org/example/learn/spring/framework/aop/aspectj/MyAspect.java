package org.example.learn.spring.framework.aop.aspectj;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * 一个cross-cutting concern用一个aspect表示,这样更加模块化
 */
@Aspect
public class MyAspect {


    @Pointcut("my-pointcut-expression")
    public void myPointcut() {

    }

    // 可以通过pointcut的id来代替pointcut表达式
    @Before("myPointcut")
    public void record1() {
        // ...
    }

    // 直接使用pointcut表达式
    @Before("my-pointcut-expression")
    public void record2() {
        // ...
    }
}
