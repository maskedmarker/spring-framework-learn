# spring xml文件定义容器


参考文档:
https://docs.spring.io/spring-framework/reference/core/appendix/xsd-schemas.html



## mvc-xml

传统的web.xml启动spring-mvc容器时,使用的是XmlWebApplicationContext
```text
org.springframework.web.context.ContextLoader {
    static {
        ClassPathResource resource = new ClassPathResource("ContextLoader.properties", ContextLoader.class);
        defaultStrategies = PropertiesLoaderUtils.loadProperties(resource);
    }
    
    protected Class<?> determineContextClass(ServletContext servletContext) {
        contextClassName = defaultStrategies.getProperty(WebApplicationContext.class.getName());
        return ClassUtils.forName(contextClassName, ContextLoader.class.getClassLoader());
    }
}

ContextLoader.properties文件中
org.springframework.web.context.WebApplicationContext=org.springframework.web.context.support.XmlWebApplicationContext
```

普通的xml与web环境的xml还是有点不同的.
普通的xml仅仅通过xml文件提供信息,web环境的xml由普通xml和web.xml共同来提供信息.
```text
XmlWebApplicationContext extends AbstractRefreshableWebApplicationContext
	AbstractRefreshableWebApplicationContext extends AbstractRefreshableConfigApplicationContext
	
ClassPathXmlApplicationContext extends AbstractXmlApplicationContext
		AbstractXmlApplicationContext extends AbstractRefreshableConfigApplicationContext
```