# 关于AOP编程范式的介绍

In computing, aspect-oriented programming (AOP) is a programming paradigm that aims to increase modularity by allowing the separation of cross-cutting concerns.

Aspect-oriented programming entails breaking down program logic into cohesive areas of functionality (so-called concerns).
Nearly all programming paradigms support some level of grouping and encapsulation of concerns into separate, independent entities by providing abstractions (e.g., functions, procedures, modules, classes, methods) that can be used for implementing, abstracting, and composing these concerns. 
Some concerns "cut across" multiple abstractions in a program, and defy(违抗) these forms of implementation. These concerns are called cross-cutting concerns or horizontal concerns.
翻译:几乎所有的编程范式都支持某种程度的关注点分组和封装，将其分离为独立的实体，通过提供抽象（如函数、过程、模块、类、方法）来实现、抽象和组合这些关注点。有些关注点“横切”程序中的多个抽象，无法通过这些形式实现。这些关注点被称为横切关注点或水平关注点。
备注:以Java为例,可以通过类将相同关注点(concern)抽象为独立的逻辑单元(即separate concern);
一个大的功能(concern)拆分为多个类来实现,这是很直白dived-conquer思想,体现了以纵向审视问题.
但是对于cross-cutting类型的关注点,它是以横向审视问题.所以要实现cross-cutting类型的关注点,就要与多数类发生交互(因为非cross-cutting类型的关注点以纵向审视问题).



JPM模型
The advice-related component of an aspect-oriented language defines a join point model (JPM). A JPM defines three things:
1. When the advice can run. These are called join points because they are points in a running program where additional behavior can be usefully joined. Many AOP implementations support method executions and field references as join points.
2. A way to specify join points, called pointcuts. Pointcuts determine whether a given join point matches. 
3. A means of specifying code to run at a join point. AspectJ calls this advice, and can run it before, after, and around join points. 

AspectJ's join-point model
1. The join points in AspectJ include method or constructor call or execution, the initialization of a class or object, field read and write access, and exception handlers. 
2. Pointcuts are specified by combinations of primitive pointcut designators (PCDs).
3. Advice specifies to run at (before, after, or around) a join point (specified with a pointcut) certain code (specified like code in a method).
备注:JPM模型中的joint point描述中包含advice执行时点,aspectJ中的joint point只包含执行的点,执行的时在advice中.这是实现的差异.

术语:
Cross-cutting concerns
Advice
Pointcut
Aspect(The combination of the pointcut and the advice is termed an aspect)

AOP标准的接口
https://aopalliance.sourceforge.net/motivations.html

Several projects now provide AOP-related techniques such as generic proxies, interceptors, or bytecode translators.

  * ASM: a lightweight bytecode translator.
  * AspectJ: an AO source-level weaver. New Language.
  * AspectWerkz: an AO framework (bytecode-level dynamic weaver+configuration).
  * BCEL: a bytecode translator.
  * CGLIB: high-level API for class artifact manipulation and method interception.
  * JAC: an AO middleware (bytecode-level dynamic weaver+configuration+aspects). Framework.
  * Javassist: a bytecode translator with a high-level API.
  * JBoss-AOP: interception and metadata-based AO framework.
  * JMangler: a bytecode translator with a composition framework for translations.
  * Nanning: an AO weaver (framework).
  * Prose: an AO bytecode-level dynamic weaver (framework).
  * ... and many others (email me to add a new one)

All these projects have their onw goals and specificities. However, several common basic components are still usefull (and sometimes required) to build a full AO system.
we think that a standardization of the interfaces of the aspect-oriented components would be great and will bring great simplifications for the entire AOSD community, but also for all the communities that will to use AOP in a close future.


