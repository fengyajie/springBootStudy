package com.example;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

/**
 *exclude= {DataSourceAutoConfiguration.class}spring boot 会默认加载org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration这个类，
 *DataSourceAutoConfiguration类使用了@Configuration注解向spring注入了dataSource bean。因为工程中没有关于dataSource相关的配置信息，当spring创建dataSource bean因缺少相关的信息就会报错。
 */
@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
//@ComponentScan(basePackages={"com.example.dao"})
@EnableTransactionManagement//如果mybatis中service实现类加入事务注解，需要此处加入注解
@MapperScan("com.example.dao")//扫描dao,由于dao层没有实现，@MapperScan相当于配置文件<bean mapperScanComponent>,
                               //交由mybatis基于JDK动态代理的方式实现
                           //@ComponentScan,会覆盖@SpringBootApplication默认扫描同级包和子包注解
public class SpingBootStudyApplication {

	//不使用springboot默认，使用alibaba fastjson
	    @Bean  
	    public HttpMessageConverters fastJsonHttpMessageConverters() {  
	       FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();  
	       FastJsonConfig fastJsonConfig = new FastJsonConfig();  
	       fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat); 
	       
	     //处理中文乱码问题
	        List<MediaType> fastMediaTypes = new ArrayList<>();
	        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
	        fastConverter.setSupportedMediaTypes(fastMediaTypes);
	       fastConverter.setFastJsonConfig(fastJsonConfig);  
	       HttpMessageConverter<?> converter = fastConverter;  
	       return new HttpMessageConverters(converter);  
	    }  
	 
	public static void main(String[] args) {
		SpringApplication.run(SpingBootStudyApplication.class, args);
	}
}
