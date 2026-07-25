# @Configuration 注解详解


# 举例

```java
// 注意@Configuration注解也携带了@Component
@Component
public @interface Configuration {
    // ...
}
```

```java
@Configuration
public class AppConfig {
    @Bean
    public A a() { return new A(); }

    @Bean
    public B b() { return new B(a()); }
}
```

# 配置类("configuration-class")
```text
被@Configuration注释的类被称为"configuration-class"
这个名字来源于 Java-based container configuration（基于 Java 的容器配置）——它是 XML 配置的替代方案。
XML中的<beans>文件就是配置,Java 中承担同样角色的类自然就叫 "configuration class".

configuration-class的携带@Bean的方法,被称为@Bean-method.
偶尔也称为 bean-definition-method 或 bean-factory-method.

总结:
@Configuration是用来替代代传统XML配置中的<beans/>.
携带@Bean的方法用来替代代传统XML配置中的<bean factoryMethod="xxx" />

注意:
AppConfig的@Configuration注解也携带了@Component,这样AppConfig才会被spring提取了BeanDefinition,才有了后续的处理流程.🎯🎯🎯
如果没有@Configuration上的@Component注解,spring容器将无法处理被@Configuration注解的配置类.
```

## configuration-class的内存模型
```text
org.springframework.context.annotation.ConfigurationClass 是 Spring 在解析 @Configuration 类时使用的内存模型,它不是 public API,而是 ConfigurationClassParser 解析过程的中间产物.

final class ConfigurationClass {

    // 通过ASM读取.class文件的字节码获得这个配置类上所有注解的信息
	private final AnnotationMetadata metadata;
    // 找到这个 .class 文件的位置资源
	private final Resource resource;

    // 这个配置类自身作为 bean 的名称，通常由 AnnotationBeanNameGenerator 生成
    // 为 null 的情况：如果这个配置类是通过 @Import 导入的，它可能不作为一个独立的 bean 存在于容器中（只取其 @Bean 方法）🎯🎯🎯
	@Nullable
	private String beanName;
	
    // 记录是哪个（哪些）配置类通过 @Import 导入了当前类
	private final Set<ConfigurationClass> importedBy = new LinkedHashSet<>(1);
	
    // 这个配置类中所有被 @Bean 注解标注的方法
	private final Set<BeanMethod> beanMethods = new LinkedHashSet<>();

    // 通过 @ImportResource 引用的外部配置文件
    // 比如: @ImportResource("classpath:legacy-beans.xml"), Map 中会有一条 "classpath:legacy-beans.xml" ->	XmlBeanDefinitionReader.class
	private final Map<String, Class<? extends BeanDefinitionReader>> importedResources = new LinkedHashMap<>();
    
    // 通过 @Import 导入的 ImportBeanDefinitionRegistrar 实现类
	private final Map<ImportBeanDefinitionRegistrar, AnnotationMetadata> importBeanDefinitionRegistrars = new LinkedHashMap<>();

    // 因为 @Conditional 条件不满足而被跳过的 @Bean 方法名
	final Set<String> skippedBeanMethods = new HashSet<>();
}


整体数据流

ConfigurationClassParser 解析过程
│
├── 读取 @Configuration 类 → 创建 ConfigurationClass 对象
│   ├── metadata: 注解元数据
│   ├── resource: class 文件位置
│   └── beanName: 自动生成或从 @Import 获取
│
├── 解析注解 → 填充字段
│   ├── 发现 @Bean 方法 → 加入 beanMethods
│   ├── 发现 @Import → 递归解析，设置子类的 importedBy
│   ├── 发现 @Import(Registrar) → 加入 importBeanDefinitionRegistrars
│   ├── 发现 @ImportResource → 加入 importedResources
│   └── @Conditional 不满足 → 方法名加入 skippedBeanMethods
│
└── 解析完成后 → ConfigurationClassBeanDefinitionReader 消费这些字段
    ├── 遍历 beanMethods → 为每个 @Bean 方法注册 BeanDefinition
    ├── 遍历 importedResources → 解析 XML/Groovy
    ├── 遍历 importBeanDefinitionRegistrars → 调用 registerBeanDefinitions()
    └── 检查 importedBy → 确保导入链完整
一句话总结：ConfigurationClass 是配置类解析阶段的内存模型，它把 @Configuration 类上散落的各种注解信息（@Bean、@Import、@ImportResource 等）收集到一个结构化的对象中，供后续的 loadBeanDefinitions 阶段消费。
```

```text
public class ConfigurationClassPostProcessor implements BeanDefinitionRegistryPostProcessor, PriorityOrdered, ResourceLoaderAware, BeanClassLoaderAware, EnvironmentAware {}

ConfigurationClassPostProcessor作为priority-ordered的post-processor,从BeanDefinitionRegistry中挑选携带@Configuration注解的beanDefinition,
协调ConfigurationClassParser从BeanDefinition中读取元信息构建起配置类的内存模型ConfigurationClass,然后协调ConfigurationClassBeanDefinitionReader基于内存模型ConfigurationClass的信息向BeanDefinitionRegistry注册增量BeanDefinition
```




## Full模式
```text
@Configuration的proxyBeanMethods() default true,默认是Full模式.
最终注册到spring容器中的AppConfig类实例是spring使用cglib生成的代理类的实例.


@Configuration Full 模式总览:
识别阶段：ConfigurationClassPostProcessor 解析原始配置类，提取所有@Bean方法并注册工厂 Bean 定义，全程只读取类元信息，无实例；
代理增强：后置处理器修改该配置类的 Bean 定义，将 beanClass 替换为 CGLIB 生成的子类代理；容器最终实例存入的是代理对象，原始类不实例化；
拦截机制：代理重写全部@Bean公共实例方法，统一交由ConfigurationClassInterceptor拦截；调用方法时先查容器单例池，有则直接返回，无则执行父类原方法创建并缓存；

代理类的与bean-method同名的方法并不需要携带@Bean注解,因为spring容器中用于生成Bean实例的工厂方法已经确定了(beanName指向代理类实例,方法名也确定了)
```



```text
@Configuration Full 模式 CGLIB 代理底层完整实现原理

@Bean 注解元信息根本不会迁移到代理类上，分两个阶段拆开讲，你就能完全分清「解析阶段」和「运行时代理拦截阶段」两件完全独立的事：

一、阶段 1：解析 BeanDefinition（发生在生成代理之前）
执行顺序：ConfigurationClassPostProcessor#postProcessBeanDefinitionRegistry
Spring 读取原始配置类 AppConfig.class 的字节码/注解元数据；
扫描原始类上所有带 @Bean 的方法，提取注解信息：@Bean、@Scope、@InitMethod、@DestroyMethod 等；
为每一个 @Bean 方法生成独立 ConfigurationClassBeanDefinition 注册到容器：
    factoryBeanName：配置类在容器中的 beanName（比如 appConfig） (🎯🎯注意: 这里用的是beanName/methodName,就是一个间接引用,为后续替换代理配置类提供了操作空间)
    factoryMethodName：方法名 a / b
所有注解元数据全部绑定原始类的原始方法，和后续代理类无关
重点：这一步完全不碰 CGLIB，代理还没生成；Spring 识别哪些方法是工厂 Bean，只认原始类，不认代理子类。

二、阶段 2：生成 CGLIB 代理（解析完 BeanDefinition 之后）
执行顺序：ConfigurationClassPostProcessor#enhanceConfigurationClasses
只修改配置类自身的 BeanDefinition：把 beanClass 从 AppConfig 替换成 CGLIB 生成的子类 AppConfig$$SpringCGLIB$$0；
代理子类只是字节码层面重写 a ()、b () 方法，但不会复制 @Bean 注解到代理类的重写方法上；
代理类的 a()、b() 方法上没有任何注解；
它只是转发调用拦截器 ConfigurationClassInterceptor；
容器最终实例化出来的配置类实例是代理对象，但所有创建 A、B Bean 的元数据依然绑定原始 AppConfig 的原始方法。
```

```text
解析阶段：存的不是单纯字符串，是关联元数据
你说 “只存 beanName 和方法名，不用 Method 对象” 不完全准确：
解析时扫描原始 Class的 @Bean 方法，拿到完整 Method 反射对象；
生成 ConfigurationClassBeanDefinition，里面记录：
factoryBeanName：配置类的 bean 名称（如 appConfig）
factoryMethodName：方法名字符串 "a" / "b"
同时缓存原始 Method 的元信息（注解、参数、返回类型等）
注册 BeanDefinition 时不持有运行时 Method 对象，只保留类名 + 方法名字符串，后续创建 Bean 时再反射查找方法。

关键点：解析阶段只关心原始类，此时还没有代理，代理是解析全部完成后才生成的。


代理替换：配置类自身的 BeanDefinition 被替换
容器里原本有一条 BeanDefinition：
beanClass = AppConfig.class
beanName = appConfig
ConfigurationClassPostProcessor 增强逻辑：
生成 CGLIB 子类 AppConfig$$SpringCGLIB$$0；
原地修改这条 BeanDefinition 的 beanClass，替换成代理类；
beanName 保持不变，依然是 appConfig；
之后容器实例化 appConfig 时，new 的是代理对象，不是原始 AppConfig。
这一点你的理解完全正确：同名，只是把实例换成代理类。
```