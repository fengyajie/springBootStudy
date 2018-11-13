package com.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@ComponentScan(basePackages={"com.example.dao"})
@MapperScan("com.example.dao")//扫描dao,由于dao层没有实现，@MapperScan相当于配置文件<bean>,
                               //交由mybatis基于JDK动态代理的方式实现
                           //@ComponentScan,会覆盖@SpringBootApplication默认扫描同级包和子包注解
public class SpingBootStudyApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpingBootStudyApplication.class, args);
	}
}
