# 常用注解

注解上的注解被spring称为meta-annotation.比如@Configuration上的@Component就是meta-annotation.


## @Bean

In the Spring Framework, the @Bean annotation is designed to be used at the method level within a @Configuration-annotated class. 
Using @Bean at the class level is not valid and will not have the intended effect.
@Bean methods may also be declared within classes that are not annotated with @Configuration.

## @Configuration

In the Spring Framework, the @Configuration annotation is used to define a class as a configuration class. 
A configuration class is a special type of component that declares one or more @Bean methods and is processed by the Spring container to generate and manage bean definitions. 
It is a key part of Spring's Java-based configuration.

@Configuration is meta-annotated with @Component, therefore @Configuration classes are candidates for component scanning.
@Configuration classes may not only be bootstrapped using component scanning, but may also themselves configure component scanning using the @ComponentScan annotation.


## @AliasFor

spring会保证@AliasFor的一致性.即相关的2个注解属性值会被同时赋值,且值相同.
@AliasFor的使用场景分2类,即Same Annotation/Across Annotations

In Spring, annotations often have attributes that serve similar or identical purposes. However, without explicit linking, they behave independently. 
The @AliasFor annotation establishes a direct relationship between these attributes, either:
- Within the same annotation, so that attributes can act as synonyms.
- Across different annotations, so that setting one attribute also configures the corresponding attribute in the meta-annotation.

Spring validates alias definitions at runtime to ensure they are consistent and bidirectional. 
If the relationship is misconfigured, an exception is thrown during application startup.

### Aliases Within the Same Annotation

Aliased attributes must point to each other if defined in the same annotation.
既然一个属性是另一个的alias,也就意味着另一个也是这个的alias.所以必须同时互相声明为alias

Here, value and alias are interchangeable
```java
public @interface MyAnnotation {
    @AliasFor("alias")
    String value() default "";

    @AliasFor("value")
    String alias() default "";
}
```


### Aliases Across Annotations
@AliasFor can also link attributes between different annotations, helping to enforce consistency.
Across different annotations, so that setting one attribute also configures the corresponding attribute in the meta-annotation.(开发者为一个注解属性赋值,等价于同时为另一个注解的属性也赋值了)

@AliasFor only works with Spring-managed annotations.
Extend existing annotations(比如MyCustomRequestMapping)

Across Annotations场景下,@AliasFor只能用于Spring-managed annotations.
Across Annotations场景下,@AliasFor起到了类似于extend现有注解的功能.

```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@RequestMapping
public @interface MyCustomRequestMapping {

    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] value() default {};

    @AliasFor(annotation = RequestMapping.class, attribute = "method")
    RequestMethod[] method() default {RequestMethod.GET};
}
```


