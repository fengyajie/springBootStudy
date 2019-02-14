package com.example.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:redis.properties")//加载自定义配置文件
public class RedisConfiguration {

	//只是加载不需要实现
}
