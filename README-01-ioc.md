# spring-ioc


## 主要概念

### BeanDefinition

A bean definition essentially is a recipe for creating one or more objects. 
The container looks at the recipe for a named bean when asked, and uses the configuration metadata encapsulated by that bean definition to create (or acquire) an actual object.

描述待创建实例的metadata.spring容器可以基于此metadata创建出用户需要的实例.
这些metadata从哪里来?
这些metadata在被用来创建实例前,可以通过BeanFactoryPostProcessor来修改