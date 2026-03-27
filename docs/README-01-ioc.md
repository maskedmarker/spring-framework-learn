# spring-ioc

```text
当我们在讨论IOC的时候, 我们是站在用户代码的视角. 
spring IOC容器是用户代码直接面对/使用的那个类. 更具体点讲,指的是就是ApplicationContext而非BeanFactory🎯🎯🎯🔥🔥🔥 (ApplicationContext继承了BeanFactory接口是为了方便read-only操作,比如提取Bean) 
ApplicationContext内部会去维护一个仅供自己使用的可配置的但不对外暴漏的BeanFactory.
```


## ApplicationContext

```text
ApplicationContext是spring提供给应用代码使用的client端的IOC容器接口;而对于IOC容器实现端,使用的ConfigurableApplicationContext接口.🎯🎯🎯
ConfigurableApplicationContext接口为IOC实现者提供了构建IOC容器所需的基本标准接口(setParent/setEnvironment::ConfigurableEnvironment/setClassLoader/refresh/registerShutdownHook/getBeanFactory::ConfigurableListableBeanFactory/addApplicationListener).
普通的应用代码应该使用ApplicationContext接口;而框架类实现者可能会用到更偏内部的ConfigurableApplicationContext接口.


spring的IOC容器都是ConfigurableApplicationContext接口的实现类(AbstractApplicationContext是其实现层级中最顶端的抽象类).


spring的IOC容器内部会去维护一个仅供自己使用的可配置的但不对外暴漏的ConfigurableListableBeanFactory(由ConfigurableApplicationContext.getBeanFactory::ConfigurableListableBeanFactory定义的).
如果是IOC容器使用方的话,通过client端的ApplicationContext接口的getBean方法来获取bean;如果是IOC容器实现者或者框架实现者,直接操作ConfigurableListableBeanFactory.


spring的IOC容器通过组合方式(ConfigurableListableBeanFactory+ConfigurableEnvironment+BeanFactoryPostProcessor+MessageSource+ApplicationListener)来构建高扩展性的容器.在IOC容器对外提供服务前,在容器初始化阶段悄无声息地完成各种定制化逻辑.
```


## ConfigurableListableBeanFactory

```text
BeanFactory
The root interface for accessing a Spring bean container (read-only操作)

ConfigurableBeanFactory extends HierarchicalBeanFactory
This bean factory interface is not meant to be used in normal application code.This extended interface is just meant to allow for framework-internal plug play and for special access to bean factory configuration methods.

AutowireCapableBeanFactory
Extension of the BeanFactory interface to be implemented by bean factories that are capable of autowiring.
This subinterface of BeanFactory is not meant to be used in normal application code.

ConfigurableListableBeanFactory extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory.

bean的自动装配(autowiring)功能位于ConfigurableListableBeanFactory层,而非ConfigurableApplicationContext层.🎯🎯🎯🔥🔥🔥
```

```text
ConfigurableListableBeanFactory的唯一实现类DefaultListableBeanFactory
```



### BeanDefinition

A bean definition essentially is a recipe for creating one or more objects. 
The container looks at the recipe for a named bean when asked, and uses the configuration metadata encapsulated by that bean definition to create (or acquire) an actual object.

描述待创建实例的metadata.spring容器可以基于此metadata创建出用户需要的实例.
这些metadata还从哪里来?
这些metadata在被用来创建实例前,还可以通过BeanFactoryPostProcessor来修改











