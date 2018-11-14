package com.example.configuration;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

/**
 *由于在启动类禁用了数据源自动配置，所以需要此类配置多数据源 
 */
@Configuration
public class DataSourceConfig {
	
	
	//数据源1
	@Bean(name="datasource1")
	@ConfigurationProperties(prefix="spring.datasource.db1")//application.properties前缀
	public DataSource dataSource1() {
		return DataSourceBuilder.create().build();
	}
	
	//数据源2
	@Bean(name="datasource2")
	@ConfigurationProperties(prefix="spring.datasource.db2")//application.properties前缀
	public DataSource dataSource2() {
		return DataSourceBuilder.create().build();
	}
	
	/**
	 * 动态数据源：通过aop在不同数据源之间切换
	 * @return
	 */
	@Primary
	@Bean(name="dynamicDataSource")
	public DataSource dynamicDataSource() {
		DynamicDataSource dynamicDataSource = new DynamicDataSource();
		//默认数据源
		dynamicDataSource.setDefaultTargetDataSource(dataSource1());
		//配置多数据源
		Map<Object,Object> dsMap = new HashMap<Object,Object>();
		dsMap.put("datasource1", dataSource1());
		dsMap.put("datasource2", dataSource2());
		dynamicDataSource.setDefaultTargetDataSource(dsMap);
		return dynamicDataSource;
	}
	
 
	/**
	 * 配置@Transactional注解事务
	 * @return
	 */
	@Bean
	public PlatformTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dynamicDataSource());
	}
}
