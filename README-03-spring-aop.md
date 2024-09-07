# spring-aop

https://docs.spring.io/spring-framework/reference/core/aop/introduction-defn.html
spring的官方doc中对于aop的解释
Aspect-oriented Programming (AOP) complements Object-oriented Programming (OOP) by providing another way of thinking about program structure. 
The key unit of modularity in OOP is the class, whereas in AOP the unit of modularity is the aspect. 
Aspects enable the modularization of concerns (such as transaction management) that cut across multiple types and objects. (Such concerns are often termed "crosscutting" concerns in AOP literature.)

aop与oop是两种不同的关于程序结构的思考方式.
"Aspects enable the modularization of concerns (such as transaction management) that cut across multiple types and objects."这句话中是基于OOP范式下,来描述aop会与很多个类相交.
OOP是主体编程范式,aop是补充范式.



## AOP Concepts

### Aspect: 
A modularization of a concern that cuts across multiple classes.
(aspect即表述cross-cutting的conern的术语.在使用注解时,可以将同一个concern的pointcut/advice定义在同一个类里,这个类代表了concern的全部信息)

### Join point: 
A point during the execution of a program, such as the execution of a method or the handling of an exception. 
In Spring AOP, a join point always represents a method execution.
(joint point是一个runtime的概念,即advice在程序运行中的执行时点.JoinPoint实例随着方法链的执行结束也要被销毁)
(因为java不支持c中的中断/异常信号,只支持在方法调用链条中插入concern逻辑)

### Advice: 
Action taken by an aspect at a particular join point.

### Pointcut: 
A predicate that matches join points. 
Advice is associated with a pointcut expression and runs at any join point matched by the pointcut (for example, the execution of a method with a certain name). 
The concept of join points as matched by pointcut expressions is central to AOP, and Spring uses the AspectJ pointcut expression language by default.
(是一种描述,描述哪个advice在什么时点执行)
(pointcuts determine join points of interest, and thus enable us to control when advice executes.)

### Introduction: 
Declaring additional methods or fields on behalf of a type.
Spring AOP lets you introduce new interfaces (and a corresponding implementation) to any advised object. 
For example, you could use an introduction to make a bean implement an IsModified interface, to simplify caching.
(对于advised object,spring-aop不仅支持为其添加advice逻辑,还支持为其添加interface.cross-cutting concern不仅会以advice method的代码片段出现,还能以额外interface method的形式出现)

### Target object: 
An object being advised by one or more aspects. 
Also referred to as the "advised object". Since Spring AOP is implemented by using runtime proxies, this object is always a proxied object.
(spring-aop是以proxy的形式实现的)

### AOP proxy: 
An object created by the AOP framework in order to implement the aspect contracts (advice method executions and so on). 
In the Spring Framework, an AOP proxy is a JDK dynamic proxy or a CGLIB proxy.

### Weaving: 
linking aspects with other application types or objects to create an advised object. 
This can be done at compile time (using the AspectJ compiler, for example), load time, or at runtime. 
Spring AOP, like other pure Java AOP frameworks, performs weaving at runtime.

The concept of join points, matched by pointcuts, is the key to AOP which distinguishes it from older technologies offering only interception. 
Pointcuts enable advice to be targeted independently of the Object-Oriented hierarchy. 
For example, an around advice providing declarative transaction management can be applied to a set of methods spanning multiple objects (such as all business operations in the service layer).


### Advisors
The concept of "advisors" does not have a direct equivalent in AspectJ.
An advisor is like a small self-contained aspect that has a single piece of advice.
(Advisor是aspect在spring中的实现类)


## Types of advice

### Before advice: 
Advice that executes before a join point, but which does not have the ability to prevent execution flow proceeding to the join point (unless it throws an exception).

### After returning advice: 
Advice to be executed after a join point completes normally: for example, if a method returns without throwing an exception.

### After throwing advice: 
Advice to be executed if a method exits by throwing an exception.

### After (finally) advice: 
Advice to be executed regardless of the means by which a join point exits (normal or exceptional return).

### Around advice: 
Advice that surrounds a join point such as a method invocation. 
This is the most powerful kind of advice. Around advice can perform custom behavior before and after the method invocation. 
It is also responsible for choosing whether to proceed to the join point or to shortcut the advised method execution by returning its own return value or throwing an exception.


## Spring AOP Capabilities and Goals

Spring AOP is implemented in pure Java. There is no need for a special compilation process. 
Spring AOP does not need to control the class loader hierarchy and is thus suitable for use in a servlet container or application server.

Spring AOP currently supports only method execution join points (advising the execution of methods on Spring beans). 
Field interception is not implemented, although support for field interception could be added without breaking the core Spring AOP APIs. 
If you need to advise field access and update join points, consider a language such as AspectJ.

Spring AOP’s approach to AOP differs from that of most other AOP frameworks. 
The aim is not to provide the most complete AOP implementation (although Spring AOP is quite capable). 
Rather, the aim is to provide a close integration between AOP implementation and Spring IoC, to help solve common problems in enterprise applications.

spring-aop并不是为了实现一个完成的aop功能框架.spring-aop功能是为了解决企业应用中cross-cutting concern的问题.
spring-aop采用了aop中部分概念,通过proxy的方式来解决cross-cutting concern的问题.

## AOP Proxies
Spring AOP defaults to using standard JDK dynamic proxies for AOP proxies. This enables any interface to be proxied.
Spring AOP can also use CGLIB proxies. This is necessary to proxy classes rather than interfaces. By default, CGLIB is used if a business object does not implement an interface.

It is important to grasp the fact that Spring AOP is proxy-based. 



