# @Configuration 注解详解


```text
org.springframework.context.annotation.ConfigurationClassPostProcessor#processConfigBeanDefinitions


整体流程图

processConfigBeanDefinitions()
│
├── 1. 扫描所有 BeanDefinition，找配置类候选
│       ├── 已有 CONFIGURATION_CLASS_ATTRIBUTE → 跳过（已处理）
│       ├── 有 @Configuration → 标记 "full"
│       └── 有 @Component/@Import/@Bean → 标记 "lite"
│
├── 2. 按 @Order 排序
│
└── 3. do-while 循环直到无新候选
    │
    ├── parser.parse(candidates)
    │   ├── 解析 @ComponentScan → 扫描包 → 发现新类 → 注册
    │   ├── 解析 @Import(Selector) → 生成新候选
    │   ├── 递归解析父类和接口
    │   └── 收集 @Bean 方法元数据
    │
    ├── reader.loadBeanDefinitions(configClasses)
    │   ├── 注册 @Bean 方法 → BeanDefinition
    │   ├── 注册 @Import 的普通类
    │   └── 解析 @ImportResource (XML)
    │
    └── 检查是否有新注册的配置类 → 有则回到循环开始
    
    
关键理解
两阶段处理：先 parse（读取注解元数据），后 loadBeanDefinitions（注册 bean 定义）
递归解析：@ComponentScan 发现的类和 @Import 引入的类可能也是配置类，需要继续解析
full vs lite：只有 full（@Configuration，默认 proxyBeanMethods=true）才会被 CGLIB 增强，保证 @Bean 方法间的单例语义
DeferredImportSelector 在所有常规配置处理完后才执行，这是 @EnableAutoConfiguration 能工作的关键 — 它必须等用户的所有配置类解析完毕再加载自动配置    
```


```text
public void processConfigBeanDefinitions(BeanDefinitionRegistry registry) {
    List<BeanDefinitionHolder> configCandidates = new ArrayList<>();
    String[] candidateNames = registry.getBeanDefinitionNames();

    // 1. 扫描所有 BeanDefinition，找配置类候选
    for (String beanName : candidateNames) {
        BeanDefinition beanDef = registry.getBeanDefinition(beanName);
        if (beanDef.getAttribute(ConfigurationClassUtils.CONFIGURATION_CLASS_ATTRIBUTE) != null) {
            if (logger.isDebugEnabled()) {
                logger.debug("Bean definition has already been processed as a configuration class: " + beanDef);
            }
        }
        else if (ConfigurationClassUtils.checkConfigurationClassCandidate(beanDef, this.metadataReaderFactory)) {
            configCandidates.add(new BeanDefinitionHolder(beanDef, beanName));
        }
    }

    // ...

    // 2. 按 @Order 排序
    // Sort by previously determined @Order value, if applicable
    configCandidates.sort((bd1, bd2) -> {
        int i1 = ConfigurationClassUtils.getOrder(bd1.getBeanDefinition());
        int i2 = ConfigurationClassUtils.getOrder(bd2.getBeanDefinition());
        return Integer.compare(i1, i2);
    });

    // ...


    // 3. do-while 循环直到无新候选
    // 支持处理@Configuration及其携带的@Import/@ComponentScan元信息
    // Parse each @Configuration class
    ConfigurationClassParser parser = new ConfigurationClassParser(
            this.metadataReaderFactory, this.problemReporter, this.environment,
            this.resourceLoader, this.componentScanBeanNameGenerator, registry);

    Set<BeanDefinitionHolder> candidates = new LinkedHashSet<>(configCandidates);
    Set<ConfigurationClass> alreadyParsed = new HashSet<>(configCandidates.size());
    do {
        parser.parse(candidates);
        parser.validate();

        Set<ConfigurationClass> configClasses = new LinkedHashSet<>(parser.getConfigurationClasses());
        configClasses.removeAll(alreadyParsed);

        // Read the model and create bean definitions based on its content
        if (this.reader == null) {
            this.reader = new ConfigurationClassBeanDefinitionReader(
                    registry, this.sourceExtractor, this.resourceLoader, this.environment,
                    this.importBeanNameGenerator, parser.getImportRegistry());
        }
        this.reader.loadBeanDefinitions(configClasses);
        alreadyParsed.addAll(configClasses);

        candidates.clear();
        if (registry.getBeanDefinitionCount() > candidateNames.length) {
            String[] newCandidateNames = registry.getBeanDefinitionNames();
            Set<String> oldCandidateNames = new HashSet<>(Arrays.asList(candidateNames));
            Set<String> alreadyParsedClasses = new HashSet<>();
            for (ConfigurationClass configurationClass : alreadyParsed) {
                alreadyParsedClasses.add(configurationClass.getMetadata().getClassName());
            }
            for (String candidateName : newCandidateNames) {
                if (!oldCandidateNames.contains(candidateName)) {
                    BeanDefinition bd = registry.getBeanDefinition(candidateName);
                    if (ConfigurationClassUtils.checkConfigurationClassCandidate(bd, this.metadataReaderFactory) &&
                            !alreadyParsedClasses.contains(bd.getBeanClassName())) {
                        candidates.add(new BeanDefinitionHolder(bd, candidateName));
                    }
                }
            }
            candidateNames = newCandidateNames;
        }
    }
    while (!candidates.isEmpty());

    // ...
}
```