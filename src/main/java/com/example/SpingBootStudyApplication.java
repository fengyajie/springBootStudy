package com.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@ComponentScan(basePackages={"com.example"})
@MapperScan("com.example.dao")//扫描dao,dao层加注解扫描不到，原因待查,service层可以,不要用
                              //@ComponentScan,会覆盖@SpringBootApplication默认扫描同级包和子包注解
public class SpingBootStudyApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpingBootStudyApplication.class, args);
	}
}
