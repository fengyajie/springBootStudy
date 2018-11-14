package com.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *exclude= {DataSourceAutoConfiguration.class}禁用springboot默认加载的application.properties
 *单数据源配置
 */
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
//@ComponentScan(basePackages={"com.example.dao"})
@EnableTransactionManagement//如果mybatis中service实现类加入事务注解，需要此处加入注解
@MapperScan("com.example.dao.customerShiro")//扫描dao,由于dao层没有实现，@MapperScan相当于配置文件<bean mapperScanComponent>,
                               //交由mybatis基于JDK动态代理的方式实现
                           //@ComponentScan,会覆盖@SpringBootApplication默认扫描同级包和子包注解
public class SpingBootStudyApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpingBootStudyApplication.class, args);
	}
}
