# spring容器启动


## ConfigurableApplicationContext

ApplicationContext接口更多定义了该如何使用容器,但是没有定义如何配置容器;ConfigurableApplicationContext接口把如何配置容器的缺口补上了.
ConfigurableApplicationContext内部使用了ConfigurableListableBeanFactory(可配可查的BeanFactory)
```text
public interface ConfigurableApplicationContext extends ApplicationContext, Lifecycle, Closeable {

    // 提供操作入口: BeanFactoryPostProcessor
    void addBeanFactoryPostProcessor(BeanFactoryPostProcessor postProcessor);
    
    // 加载配置
    void refresh() throws BeansException, IllegalStateException;
    
    // 提供操作入口: 可配可查的BeanFactory
    ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException;
}
```

### AbstractApplicationContext
ConfigurableApplicationContext的一个主要实现AbstractApplicationContext

AbstractApplicationContext.refresh完成了主要的逻辑:
1. 准备ConfigurableEnvironment
2. 准备ConfigurableListableBeanFactory
3. 主动实例化non-lazy-init bean, (BeanFactory在用户使用时才实例化)
4. 发布refresh消息(包括发布ContextRefreshedEvent和调用Lifecycle对应的方法)


```text
org.springframework.context.support.AbstractApplicationContext.refresh

public void refresh() throws BeansException, IllegalStateException {
    synchronized (this.startupShutdownMonitor) {
        // Prepare this context for refreshing.
        prepareRefresh();

        // Tell the subclass to refresh the internal bean factory.
        ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();

        // Prepare the bean factory for use in this context.
        prepareBeanFactory(beanFactory);

        try {
            // Allows post-processing of the bean factory in context subclasses.
            postProcessBeanFactory(beanFactory);

            // Invoke factory processors registered as beans in the context.
            invokeBeanFactoryPostProcessors(beanFactory);

            // Register bean processors that intercept bean creation.
            registerBeanPostProcessors(beanFactory);

            // Initialize message source for this context.
            initMessageSource();

            // Initialize event multicaster for this context.
            initApplicationEventMulticaster();

            // Initialize other special beans in specific context subclasses.
            onRefresh();

            // Check for listener beans and register them.
            registerListeners();

            // Instantiate all remaining (non-lazy-init) singletons.(主动实例化bean)
            finishBeanFactoryInitialization(beanFactory);

            // Last step: publish corresponding event.
            finishRefresh();
        }

        catch (BeansException ex) {
            if (logger.isWarnEnabled()) {
                logger.warn("Exception encountered during context initialization - " +
                        "cancelling refresh attempt: " + ex);
            }

            // Destroy already created singletons to avoid dangling resources.
            destroyBeans();

            // Reset 'active' flag.
            cancelRefresh(ex);

            // Propagate exception to caller.
            throw ex;
        }

        finally {
            // Reset common introspection caches in Spring's core, since we
            // might not ever need metadata for singleton beans anymore...
            resetCommonCaches();
        }
    }
}

org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization

protected void finishBeanFactoryInitialization(ConfigurableListableBeanFactory beanFactory) {
    // Initialize conversion service for this context.
    if (beanFactory.containsBean(CONVERSION_SERVICE_BEAN_NAME) &&
            beanFactory.isTypeMatch(CONVERSION_SERVICE_BEAN_NAME, ConversionService.class)) {
        beanFactory.setConversionService(
                beanFactory.getBean(CONVERSION_SERVICE_BEAN_NAME, ConversionService.class));
    }

    // Register a default embedded value resolver if no bean post-processor
    // (such as a PropertyPlaceholderConfigurer bean) registered any before:
    // at this point, primarily for resolution in annotation attribute values.
    if (!beanFactory.hasEmbeddedValueResolver()) {
        beanFactory.addEmbeddedValueResolver(strVal -> getEnvironment().resolvePlaceholders(strVal));
    }

    // Initialize LoadTimeWeaverAware beans early to allow for registering their transformers early.
    String[] weaverAwareNames = beanFactory.getBeanNamesForType(LoadTimeWeaverAware.class, false, false);
    for (String weaverAwareName : weaverAwareNames) {
        getBean(weaverAwareName);
    }

    // Stop using the temporary ClassLoader for type matching.
    beanFactory.setTempClassLoader(null);

    // Allow for caching all bean definition metadata, not expecting further changes.
    beanFactory.freezeConfiguration();

    // Instantiate all remaining (non-lazy-init) singletons.
    beanFactory.preInstantiateSingletons();
}
```

