package com.example.configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * 你不需要将所有的 @Configuration 放进一个单独的类， @Import 注解可以用来
*导入其他配置类。另外，你也可以使用 @ComponentScan 注解自动收集所有
*Spring组件，包括 @Configuration 类

如果必须使用XML配置，建议你仍旧从一个 @Configuration 类开始，然后使
用 @ImportResource 注解加载XML配置文件。

Spring Boot自动配置（auto-configuration） 尝试根据添加的jar依赖自动配置你的
Spring应用。例如，如果classpath下存在 HSQLDB ，并且你没有手动配置任何数据
库连接的beans，那么Spring Boot将自动配置一个内存型（in-memory） 数据库。
实现自动配置有两种可选方式，分别是
将 @EnableAutoConfiguration 或 @SpringBootApplication 注解
到 @Configuration 类上。
注：你应该只添加一个 @EnableAutoConfiguration 注解，通常建议将它添加到
主配置类（primary @Configuration ） 上。



 *如果发现启用了不想要的自动配置，可以使用@EnableAutoConfiguration的exclude属性禁用
 *如果该类不在classpath中，你可以使用该注解的excludeName属性，并指定全限定
*名来达到相同效果。最后，你可以通过 spring.autoconfigure.exclude 属性
*exclude多个自动配置项（一个自动配置项集合） 
*/
@Configuration
@EnableAutoConfiguration(exclude= {DataSourceAutoConfiguration.class})
public class MyConfiguration {

}
