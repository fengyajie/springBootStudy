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
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;


@Configuration
@MapperScan(basePackages="com.example.dao.customerSpringboot",sqlSessionTemplateRef="springbootSqlSessionTemplate")
public class MybatisConfig2 {

	@Bean(name="springbootDataSource")
	@ConfigurationProperties(prefix="spring.datasource.springboot")
	public DataSource springbootDataSource(){
		return DataSourceBuilder.create().build();
	}
	
	@Bean(name="springbootSqlSessionFactory")
	public SqlSessionFactory springbootSqlSessionFactory(@Qualifier("springbootDataSource") DataSource dataSource) throws Exception{
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		sqlSessionFactoryBean.
        setMapperLocations(new PathMatchingResourcePatternResolver().
                getResources("classpath*:/myBatis/customerSpringboot/*Mapper.xml"));
		return sqlSessionFactoryBean.getObject();
	}
	
	@Bean(name="springbootTransactionManager")
	public DataSourceTransactionManager springbootTransactionManager(@Qualifier("springbootDataSource") DataSource dataSource){
		return new DataSourceTransactionManager(dataSource);
	}
	
	@Bean(name="springbootSqlSessionTemplate")
	public SqlSessionTemplate springbootSqlSessionTemplate(@Qualifier("springbootSqlSessionFactory") SqlSessionFactory sqlSessionFactory){
		return new SqlSessionTemplate(sqlSessionFactory);
	}
}
