# spring-aop

Spring AOP is proxy-based. 
It is vitally important that you grasp the semantics of what that last statement actually means before you write your own aspects 
or use any of the Spring AOP-based aspects supplied with the Spring Framework.
注意:spring的aop的重点语义是proxy(而非其他),下图说明了proxy的含义.
https://docs.spring.io/spring-framework/reference/_images/aop-proxy-call.png


The key thing to understand here is that the client code has a reference to the proxy. This means that method calls on that object reference are calls on the proxy. 
As a result, the proxy can delegate to all of the interceptors (advice) that are relevant to that particular method call. 
However, **once the call has finally reached the target object, any method calls that it may make on itself are going to be invoked against the this reference, and not the proxy**. 
This has important implications. It means that self-invocation is not going to result in the advice associated with a method invocation getting a chance to run.

spring在使用cglib时却没有实现self-invocation的aop.这是为了符合proxy的语义.
如果直接按cglib的用法来实现aop,如下2点就与spring的proxy语义相违背:
1.client code引用的是目标instance
2.this会指向所谓的proxy(当然还是目标instance)


至于spring为什么选择proxy范式,处于什么目的,不得而知,这算是一个feature吧





## 关键类
Advice
Tag interface,满足aop业界的语义???

Interceptor
Interceptor extends Advice.
A generic interceptor can intercept runtime events that occur within a base program. Those events are materialized by (reified in) joinpoints. Runtime joinpoints can be invocations, field access, exceptions..
java语言是通过

Joinpoint
A runtime joinpoint is an event that occurs on a static joinpoint (i.e. a location in a the program). 

