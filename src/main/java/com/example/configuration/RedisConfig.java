package com.example.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * SpringBoot自动帮我们在容器中生成了一个RedisTemplate和一个StringRedisTemplate。
 * 但是，这个RedisTemplate的泛型是<Object,Object>，写代码不方便，需要写好多类型转换的代码；
 * 我们需要一个泛型为<String,Object>形式的RedisTemplate。并且，这个RedisTemplate没有设置数据存在Redis时，key及value的序列化方式
 * 
 * @author fyj
 *
 */
@EnableCaching
@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class RedisConfig extends CachingConfigurerSupport {

	@Value("${spring.redis.sentinel.nodes}")
	private String redisNodes;

	@Value("${spring.redis.sentinel.master}")
	private String master;

	@Value("${spring.redis.cluster.nodes}")
	private String clusterNodes;

	// 网上学习的结果都有这段代码，但是去掉又不影响什么，应该是不需要
	/*
	 * @Bean public RedisSentinelConfiguration redisSentinelConfiguration(){
	 * RedisSentinelConfiguration configuration = new RedisSentinelConfiguration();
	 * String[] host = redisNodes.split(","); for(String redisHost : host){ String[]
	 * item = redisHost.split(":"); String ip = item[0]; String port = item[1];
	 * configuration.addSentinel(new RedisNode(ip, Integer.parseInt(port))); }
	 * configuration.setMaster(master); return configuration; }
	 */

	@Bean
	public RedisTemplate<String,Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
		RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(lettuceConnectionFactory);

		FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);
		//redisTemplate.setValueSerializer(fastJsonRedisSerializer); //
		// 使用StringRedisSerializer来序列化和反序列化redis的key值
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashValueSerializer(fastJsonRedisSerializer);
		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}

	/**
	 * 配置缓存管理器
	 * 
	 * @param redisConnectionFactory
	 * @return
	 *//*
		 * @Bean public CacheManager cacheManager(RedisTemplate redisTemplate) {
		 * RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate);
		 * return redisCacheManager; }
		 */
	/**
	 * 缓存的key是 包名+方法名+参数列表
	 */
	@Bean
	public KeyGenerator keyGenerator() {
		return (target, method, objects) -> {
			StringBuilder sb = new StringBuilder();
			sb.append(target.getClass().getName());
			sb.append("::" + method.getName() + ":");
			for (Object obj : objects) {
				sb.append(obj.toString());
			}
			return sb.toString();
		};
	}
}
