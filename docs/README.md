# 关于spring framework的学习

## 参考
https://docs.spring.io/spring-framework/reference/


## 关键概念
Inversion of Control (IoC)
Aspect-Oriented Programming (AOP)

## Inversion of Control (IoC) container
IoC is also known as dependency injection (DI).
The container injects those dependencies when it creates the bean. This process is fundamentally the inverse (hence the name, Inversion of Control) of the bean itself controlling the instantiation or location of its dependencies by using direct construction of classes or a mechanism such as the Service Locator pattern.
The org.springframework.beans and org.springframework.context packages are the basis for Spring Framework’s IoC container.

## Aspect-Oriented Programming (AOP) technologies








# 临时记录
## Environment
Environment: Interface representing the environment in which the current application is running. Models two key aspects of the application environment: profiles and properties. Methods related to property access are exposed via the PropertyResolver superinterface.
A profile is a named, logical group of bean definitions to be registered with the container only if the given profile is active.
Beans may be assigned to a profile whether defined in XML or via annotations; see the spring-beans 3.1 schema or the @Profile annotation for syntax details.
The role of the Environment object with relation to profiles is in determining which profiles (if any) are currently active, and which profiles (if any) should be active by default.
Properties play an important role in almost all applications, and may originate from a variety of sources: properties files, JVM system properties, system environment variables, JNDI, servlet context parameters, ad-hoc Properties objects, Maps, and so on. The role of the environment object with relation to properties is to provide the user with a convenient service interface for configuring property sources and resolving properties from them.
Beans managed within an ApplicationContext may register to be EnvironmentAware or @Inject the Environment in order to query profile state or resolve properties directly.In most cases, however, application-level beans should not need to interact with the Environment directly but instead may have to have ${...} property values replaced by a property placeholder configurer such as PropertySourcesPlaceholderConfigurer, which itself is EnvironmentAware and as of Spring 3.1 is registered by default when using <context:property-placeholder/>.
Properties是泛指k-v形式的配置参数集合
### PropertySource
PropertySource
EnumerablePropertySource
MapPropertySource(构造函数的入参是Map类型的)
  PropertiesPropertySource(构造函数的入参是Properties)
  ResourcePropertySource(构造函数的入参是properties文件的路径)
SystemEnvironmentPropertySource(构造函数的入参不是Map类型的,可能是个字符串)
  SimpleCommandLinePropertySource
  JOptCommandLinePropertySource




## AttributeAccessor
Interface defining a generic contract for attaching and accessing metadata to/from arbitrary objects.
AttributeAccessor实例与目标对象是独立的,AttributeAccessor实例单独保有目标对象的metadata(属性名及其值)