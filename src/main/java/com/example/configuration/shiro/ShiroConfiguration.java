package com.example.configuration.shiro;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.configuration.shiro.realm.UserRealm;

/**
 * shiro配置类
 * @author fyj
 *
 */
@Configuration
public class ShiroConfiguration {

	/**
	 * session会话管理，session交给shiro
	 * @param globalSessionTimeout
	 * @return
	 */
	@Bean
	public DefaultWebSessionManager sessionManager(@Value("${myframe.sessionTimeout:3600}") long globalSessionTimeout) {
		DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
		//是否开启会话验证器，默认开启
		defaultWebSessionManager.setSessionValidationSchedulerEnabled(true);
		//禁止URL地址后面添加JSESSIONID
		defaultWebSessionManager.setSessionIdUrlRewritingEnabled(false);
		//设置全局会话超时时间
		defaultWebSessionManager.setGlobalSessionTimeout(globalSessionTimeout*1000);
		defaultWebSessionManager.setSessionValidationInterval(globalSessionTimeout*1000);
		return defaultWebSessionManager;
	}
	
	/**
	 * 安全管理器
	 * @param userRealm
	 * @param sessionManager
	 * @return
	 */
	@Bean("securityManager")
	public SecurityManager securityManager(UserRealm userRealm,SessionManager sessionManager) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(userRealm);
		securityManager.setRememberMeManager(null);
		securityManager.setSessionManager(sessionManager);
		return securityManager;
	}
	
	@Bean("shiroFilter")
	public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
		shiroFilter.setSecurityManager(securityManager);
		shiroFilter.setLoginUrl("/login.html");
		shiroFilter.setUnauthorizedUrl("/");
		
		Map<String,String> filterMap = new LinkedHashMap<>();
		filterMap.put("/static/**", "anon");
		filterMap.put("/templates/hello.html", "anon");
	    filterMap.put("/**", "authc");
	    shiroFilter.setFilterChainDefinitionMap(filterMap);
	    return shiroFilter;
	}
	
	/**
	 * shiro生命周期处理器，保证实现了Shiro内部lifecycle函数的bean执行
	 * @return
	 */
	@Bean("lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}
	
	/**
	 * 启用shrio授权注解拦截方式，AOP式方法级权限检查
	 * @return
	 */
	@Bean("defaultAdvisorAutoProxyCreator")
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
		defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
		return defaultAdvisorAutoProxyCreator;
	}
	
	/**
	 * 加入注解的使用，不加入这个注解不生效 使用shiro框架提供的切面类，用于创建代理对象
	 * @param securityManager
	 * @return
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}
}
