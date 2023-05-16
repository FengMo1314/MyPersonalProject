package com.example.springbootweb.util;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/*
  @Inherited：在使用此自定义注解时，如果注解在类上面时，子类会自动继承此注解，否则，子类不会继承此注解。这里一定要记住，使用Inherited声明出来的注解，只有在类上使用时才会有效，对方法，属性等其他无效。
 * @Target：表示此注解可以放置的位置。常见的位置有：TYPE=枚举或注解上，FIELD=字段上，METHOD=方法上，PARAMETER=函数形参列表中，CONSTRUCTOR=构造函数上，LOCAL_VARIABLE=局部变量上 等等其他位置。
 * @Retention：此注解的生命周期。常见的有：SOURCE=源码时期；CLASS=字节码时期（已编译）；RUNTIME=运行时期，通常是用这个的时候要多。
 * @Documentd：生成注解文档。
 */

/**
 * 自定义权限标注注解类
 * @Documented – 注解是否将包含在JavaDoc中
 * @Retention – 什么时候使用该注解
 * @Target – 注解用于什么地方
 * @Inherited – 是否允许子类继承该注解
 * @Repeatable - 是否可重复注解，jdk1.8引入
 */

/**
 * @Inherited，定义该注解和子类的关系，使用此注解声明出来的自定义注解，在使用在类上面时，子类会自动继承此注解，否则，子类不会继承此注解。注意，使用Inherited声明出来的注解，只有在类上使用时才会有效，对方法，属性等其他无效。
 */
//@Inherited
/**
 * 通过@Target定义注解作用的目标，比如作用于类、属性、或方法等，默认可用于任何地方。格式如下：
 *
 * @Target(ElementType.TYPE)
 * 对应ElementType参数值适用范围如下：
 *
 * ElementType.TYPE: 类、接口、注解、enum
 * ElementType.CONSTRUCTOR: 构造函数
 * ElementType.FIELD: 成员变量、对象、属性、枚举的常量
 * ElementType.LOCAL_VARIABLE: 局部变量
 * ElementType.METHOD: 方法
 * ElementType.PACKAGE: 包
 * ElementType.PARAMETER: 参数
 * ElementType.ANNOTATION_TYPE): 注解
 * ElementType.TYPE_PARAMETER：类型参数，表示这个注解可以用在 Type的声明式前,jdk1.8引入。
 * ElementType.TYPE_USE：类型的注解，表示这个注解可以用在所有使用Type的地方（如：泛型，类型转换等），jdk1.8引入。
 */
@Target({ElementType.TYPE, ElementType.METHOD})
/**
 * 通过@Retention定义注解的生命周期，格式如下：
 *
 * @Retention(RetentionPolicy.SOURCE)
 * 其中RetentionPolicy的不同策略对应的生命周期如下：
 *
 * RetentionPolicy.SOURCE : 仅存在于源代码中，编译阶段会被丢弃，不会包含于class字节码文件中。@Override, @SuppressWarnings都属于这类注解。
 * RetentionPolicy.CLASS : 默认策略，在class字节码文件中存在，在类加载的时被丢弃，运行时无法获取到。
 * RetentionPolicy.RUNTIME : 始终不会丢弃，可以使用反射获得该注解的信息。自定义的注解最常用的使用方式。
 */
@Retention(RetentionPolicy.RUNTIME)
/**
 * @Documented，表示是否将此注解的相关信息添加到javadoc文档中。
 */
@Documented
public @interface LHAuth {
     String[] value() default{};
}