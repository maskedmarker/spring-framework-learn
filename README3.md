# 关于spring framework的模块说明

## beans
https://docs.spring.io/spring-framework/reference/core/beans/definition.html

## property
java.util.Properties
The Properties class represents a persistent set of properties(a list of key-value pair)
java.util.Properties表示具有持久化机制的多个k-v对(property)的集合,核心思想是可以将数据持久化到文件中,通过拥有从文件读取数据.
java.util.Properties并没有明确说明该类的k-v数据是用来做配置项的.

org.springframework.core.env.PropertySource
Abstract base class representing a source of name/value property pairs
PropertySource identity is determined not based on the content of encapsulated properties, but rather based on the name of the PropertySource alone
spring中对于Property更倾向于认为k-v对为配置项