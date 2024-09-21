# spring-ioc


## 主要概念

### BeanDefinition

A bean definition essentially is a recipe for creating one or more objects. 
The container looks at the recipe for a named bean when asked, and uses the configuration metadata encapsulated by that bean definition to create (or acquire) an actual object.

描述待创建实例的metadata.spring容器可以基于此metadata创建出用户需要的实例.
这些metadata从哪里来?
这些metadata在被用来创建实例前,可以通过BeanFactoryPostProcessor来修改

### 容器类

spring为ioc概念提供了众多的接口:
BeanFactory




ApplicationContext和BeanFactory都是面向应用代码的提供容器的接口. 
BeanFactory仅仅提供了最基本的getBean功能;
ApplicationContext还支持查看bean definitions ConfigurableApplicationContext支持BeanFactoryPostProcessor(Configuration and lifecycle methods are encapsulated here to avoid making them obvious to ApplicationContext client code)

#### BeanFactory

The root interface for accessing a Spring bean container
该interface仅仅定义了spring ioc容器最基础/最简单的功能.即getBean/isSingleton/isPrototype这类最最基础的接口方法.

#### HierarchicalBeanFactory

HierarchicalBeanFactory extends BeanFactory
该interface定义了spring ioc容器之间支持父子层级关系.
并为父子层级提供了最重要/最基础的2个方法.即getParentBeanFactory提供了获取父容器的方法;containsLocalBean提供了判断当前容器(不涵盖父容器)是否含有某个bean.
但是并没有预设/假设/约束/限定getBean的时候会从父容器获取bean.抽象的非常克制.

#### ConfigurableBeanFactory

ConfigurableBeanFactory extends HierarchicalBeanFactory

This bean factory interface is not meant to be used in normal application code.This extended interface is just meant to allow for framework-internal plug play and for special access to bean factory configuration methods.
spring ioc容器在为供应用代码提供使用方法之前,spring ioc容器自身需要先被正确地配置/初始化.
容器的配置方法与使用方法被定义在了不同interface中,这样容器的使用方可以获取到更清晰的接口.体现了设计中的注意力隔离的思想.
容器的使用方通常是一般的应用代码,而容器的配置方通常是框架类代码.前者主要用来实现业务逻辑,侧重容器的使用;而后者主要实现框架级别的通用功能,常常需要对spring ioc容器做定制化配置和扩展.
应用代码不要使用该接口,应该使用BeanFactory或者ListableBeanFactory

主要方法有:
setParentBeanFactory/setBeanClassLoader/addBeanPostProcessor/registerDependentBean/setConversionService/registerCustomEditor/addEmbeddedValueResolver

#### 
#### 
#### 
#### 
#### 
