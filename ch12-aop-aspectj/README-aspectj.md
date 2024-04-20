# spring-aspectj

aspectj框架对于aop的语义实现更加完整,对于cross-cutting的控制粒度更加细.

@AspectJ support
@AspectJ refers to a style of declaring aspects as regular Java classes annotated with Java 5 annotations. 
The @AspectJ style was introduced by the AspectJ project as part of the AspectJ 5 release. 
Spring 2.0 interprets the same annotations as AspectJ 5, using a library supplied by AspectJ for pointcut parsing and matching. 
The AOP runtime is still pure Spring AOP though, and there is no dependency on the AspectJ compiler or weaver.
Using the AspectJ compiler and weaver enables use of the full AspectJ language, and is discussed in Section 6.8, “Using AspectJ with Spring applications”.

Because Spring AOP limits matching to only method execution join points, the discussion of the pointcut designators above gives a narrower definition than you will find in the AspectJ programming guide. In addition, AspectJ itself has type-based semantics and at an execution join point both 'this' and 'target' refer to the same object - the object executing the method. Spring AOP is a proxy-based system and differentiates between the proxy object itself (bound to 'this') and the target object behind the proxy (bound to 'target').




## Advice types in Spring
注意区分哪些是aopalliance/spring的接口
https://docs.spring.io/spring-framework/docs/2.5.x/reference/aop-api.html#aop-api-advice-types