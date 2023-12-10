# spring-framework官方文档
https://docs.spring.io/spring-framework/reference


## Core Technologies
### The IoC Container
The org.springframework.beans and org.springframework.context packages are the basis for Spring Framework’s IoC container.
the BeanFactory provides the configuration framework and basic functionality, and the ApplicationContext adds more enterprise-specific functionality. It adds:
- Easier integration with Spring’s AOP features
- Message resource handling (for use in internationalization)
- Event publication
- Application-layer specific contexts such as the WebApplicationContext for use in web applications.
#### Container Extension Points
1. Customizing Beans by Using a BeanPostProcessor. 
      1.1The BeanPostProcessor interface defines callback methods that you can implement to provide your own (or override the container’s default) instantiation logic, dependency resolution logic, and so forth.
2. Customizing Configuration Metadata with a BeanFactoryPostProcessor. 
      2.1 the Spring IoC container lets a BeanFactoryPostProcessor read the configuration metadata and potentially change it before the container instantiates any beans other than BeanFactoryPostProcessor instances.
3. Customizing Instantiation Logic with a FactoryBean
      3.1 If you have complex initialization code that is better expressed in Java as opposed to a (potentially) verbose amount of XML, you can create your own FactoryBean, write the complex initialization inside that class, and then plug your custom FactoryBean into the container.
      3.2 When you need to ask a container for an actual FactoryBean instance itself instead of the bean it produces, prefix the bean’s id with the ampersand symbol (&) when calling the getBean() method of the ApplicationContext

#### Additional Capabilities of the ApplicationContext
To enhance BeanFactory functionality in a more framework-oriented style, the context package also provides the following functionality:
- Access to messages in i18n-style, through the MessageSource interface.
- Access to resources, such as URLs and files, through the ResourceLoader interface.
- Event publication, namely to beans that implement the ApplicationListener interface, through the use of the ApplicationEventPublisher interface.
- Loading of multiple (hierarchical) contexts, letting each be focused on one particular layer, such as the web layer of an application, through the HierarchicalBeanFactory interface.
