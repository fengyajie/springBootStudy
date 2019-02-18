package com.example.domain;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
*自定义配置文件读取测试 
*springboot 1.5.8 pom里面加了依赖，ConfigurationProperties注解已经不支持locations了，然后对象的属性值是空的。
*解决方法：在属性对象上面除了加一个@ConfigurationProperties注解外，再加个@PropertySource注解，
*并且不用再主类上加@EnableConfigurationProperties注解了
*/
@Component
@ConfigurationProperties(prefix="spring.redis")
@PropertySource(value="classpath:redis.properties")
public class PropertiesTest {

	private String host;
	
	private String port;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}
}
