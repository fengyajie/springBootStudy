package com.example.configuration.shiro;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.ExecutorServiceSessionValidationScheduler;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import com.example.configuration.RedisSessionDAO;
import com.example.configuration.shiro.realm.UserRealm;
import com.example.filter.SysUserFilter;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import cn.hutool.core.codec.Base64;

/**
 * shiro配置类
 * @author fyj
 *
 */
@Configuration
public class ShiroConfiguration {
	
	/**
	 * ShiroDialect，为了在thymeleaf里使用shiro的标签的bean
	 */
	@Bean
	public ShiroDialect shiroDialect() {
		return new ShiroDialect();
	}

	/**
	 * session会话管理，session交给shiro
	 * @param globalSessionTimeout
	 * @return
	 */
	@Bean
	public DefaultWebSessionManager sessionManager(RedisTemplate redisTemplate) {
		DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
		//是否开启会话验证器，默认开启
		defaultWebSessionManager.setSessionValidationSchedulerEnabled(true);
		//禁止URL地址后面添加SESSIONID
		defaultWebSessionManager.setSessionIdUrlRewritingEnabled(false);
		//删除失效的sessionid
		defaultWebSessionManager.setDeleteInvalidSessions(true);
		//设置全局会话超时时间
		//defaultWebSessionManager.setGlobalSessionTimeout(globalSessionTimeout*1000);
		//defaultWebSessionManager.setSessionValidationInterval(globalSessionTimeout*1000);
		//session验证
		defaultWebSessionManager.setSessionValidationSchedulerEnabled(true); 
		defaultWebSessionManager.setSessionValidationScheduler(getExecutorServiceSessionValidationScheduler()); 
		
		defaultWebSessionManager.getSessionIdCookie().setName("session-z-id");  
		defaultWebSessionManager.getSessionIdCookie().setPath("/");  
		defaultWebSessionManager.getSessionIdCookie().setMaxAge(60*60*24*7);  
		defaultWebSessionManager.setSessionDAO(redisSessionDAO(redisTemplate));
		return defaultWebSessionManager;
	}
	
	@Bean
	public ShiroRedisCache shiroRedisCache(RedisTemplate redisTemplate) {
		ShiroRedisCache shiroRedisCache = new ShiroRedisCache(redisTemplate);
		return shiroRedisCache;
	}
	
	 /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     * 使用的是shiro-redis开源插件
     */
    @Bean
    public RedisSessionDAO redisSessionDAO(RedisTemplate redisTemplate) {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setShiroRedisCache(shiroRedisCache(redisTemplate));
        return redisSessionDAO;
    }
	/**
	 * shiro提供了会话验证调度器，用于定期的验证会话是否已过期，如果过期将停止会话；出于性能考虑，一般情况下都是获取会话时来验证会话是否过期并停止会话的；
	 * 但是如在web环境中，如果用户不主动退出是不知道会话是否过期的，因此需要定期的检测会话是否过期，Shiro提供了会话验证调度器SessionValidationScheduler。
	 * @return
	 */
	@Bean(name = "sessionValidationScheduler")  
    public ExecutorServiceSessionValidationScheduler getExecutorServiceSessionValidationScheduler() {  
        ExecutorServiceSessionValidationScheduler scheduler = new ExecutorServiceSessionValidationScheduler();  
        scheduler.setInterval(900000);  
        return scheduler;  
    }
	
	@Bean
	public SysUserFilter sysUserFilter() {
		return new SysUserFilter();
	}
	
	
	
	/**
	 * 安全管理器  
	 * 配置各种manager,跟xml的配置很像，但是，这里有一个细节，就是各个set的次序不能乱
	 * @param userRealm
	 * @param sessionManager
	 * @return
	 */
	@Bean("securityManager")
	public SecurityManager securityManager(UserRealm userRealm,RedisTemplate<String,Object> redisTemplate) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		//配置 rememberMeCookie 查看源码可以知道，这里的rememberMeManager就仅仅是一个赋值，所以先执行
		securityManager.setRememberMeManager(rememberMeManager());
		//配置 缓存管理类 cacheManager，这个cacheManager必须要在前面执行，因为setRealm 和 setSessionManage都有方法初始化了cachemanager,看下源码就知道了
		securityManager.setCacheManager(cacheManager(redisTemplate));
		securityManager.setRealm(userRealm);
		securityManager.setSessionManager(sessionManager(redisTemplate));
		return securityManager;
	}
	
	private ShiroRedisCacheManager cacheManager(RedisTemplate redisTemplate) {
		return new ShiroRedisCacheManager(redisTemplate);
	}
	
	private SimpleCookie rememberMeCookie() {
		SimpleCookie simpleCookie = new SimpleCookie(CookieRememberMeManager.DEFAULT_REMEMBER_ME_COOKIE_NAME);
		//是否只在https协议下传输
		simpleCookie.setSecure(false);
		return simpleCookie;
	}
	
	private CookieRememberMeManager rememberMeManager() {
		CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
		cookieRememberMeManager.setCookie(rememberMeCookie());
		// rememberMe cookie 加密的密钥
        cookieRememberMeManager.setCipherKey(Base64.decode("ZWvohmPdUsAWT3=KpPqda"));
        return cookieRememberMeManager;
	}
	
	@Bean("shiroFilter")
	public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
		shiroFilter.setSecurityManager(securityManager);
		shiroFilter.setLoginUrl("/loginView");
		shiroFilter.setUnauthorizedUrl("/");
		
		Map<String, Filter> filterMap = new LinkedHashMap<>();
		filterMap.put("sysUser", sysUserFilter());
		shiroFilter.setFilters(filterMap);
		
		
		Map<String,String> filterChainDefinitionMap = new LinkedHashMap<>();
		filterChainDefinitionMap.put("/static/**", "anon");
		filterChainDefinitionMap.put("/loginView", "anon");
		filterChainDefinitionMap.put("/", "anon");
		filterChainDefinitionMap.put("/login", "anon");
		filterChainDefinitionMap.put("/**", "authc,sysUser");
	    shiroFilter.setFilterChainDefinitionMap(filterChainDefinitionMap);
	    return shiroFilter;
	}
	
	//解决报错：org.apache.shiro.UnavailableSecurityManagerException: No SecurityManager accessible to the calling code, either bound to the org.apache.shiro.ut
	@Bean
    public FilterRegistrationBean securityFilterChain(AbstractShiroFilter securityFilter) {
        FilterRegistrationBean registration = new FilterRegistrationBean(securityFilter);
        registration.setOrder(Integer.MAX_VALUE-1);//排在ZHandleDotDOFilter的前面
        registration.setName("shiroFilter");
        registration.setDispatcherTypes(DispatcherType.REQUEST,DispatcherType.ASYNC);
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        return registration;
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
