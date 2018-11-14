package com.example.configuration;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@MapperScan(basePackages="com.example.dao.customerShiro",sqlSessionTemplateRef="shiroSqlSessionTemplate")
public class MybatisConfig {

	@Bean(name="shiroDataSource")
	@ConfigurationProperties(prefix="spring.datasource.shiro")
	@Primary
	public DataSource shiroDataSource(){
		return DataSourceBuilder.create().build();
	}
	
	@Bean(name="shiroSqlSessionFactory")
	@Primary
	public SqlSessionFactory shiroSqlSessionFactory(@Qualifier("shiroDataSource") DataSource dataSource) throws Exception{
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		return sqlSessionFactoryBean.getObject();
	}
	
	@Bean(name="shiroTransactionManager")
	@Primary
	public DataSourceTransactionManager shiroTransactionManager(@Qualifier("shiroDataSource") DataSource dataSource){
		return new DataSourceTransactionManager(dataSource);
	}
	
	@Bean(name="shiroSqlSessionTemplate")
	public SqlSessionTemplate shiroSqlSessionTemplate(@Qualifier("shiroSqlSessionFactory") SqlSessionFactory sqlSessionFactory){
		return new SqlSessionTemplate(sqlSessionFactory);
	}
}
