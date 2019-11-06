/*
 * package com.example.configuration.shiro;
 * 
 * import org.apache.shiro.cache.Cache; import
 * org.apache.shiro.cache.CacheException; import
 * org.apache.shiro.cache.CacheManager; import org.slf4j.Logger; import
 * org.slf4j.LoggerFactory;
 * 
 * import java.util.concurrent.ConcurrentHashMap; import
 * java.util.concurrent.ConcurrentMap;
 * 
 * public class RedisCacheManager implements CacheManager {
 * 
 * private static final Logger logger = LoggerFactory
 * .getLogger(RedisCacheManager.class);
 * 
 * private final ConcurrentMap<String, Cache> caches = new
 * ConcurrentHashMap<String, Cache>();
 * 
 * private RedisManager redisManager;
 * 
 * private String keyPrefix = "report_boss_shiro_redis_cache:";
 * 
 * public String getKeyPrefix() { return keyPrefix; }
 * 
 * public void setKeyPrefix(String keyPrefix) { this.keyPrefix = keyPrefix; }
 * 
 * @Override public <K, V> Cache<K, V> getCache(String name) throws
 * CacheException { logger.debug("获取名称为: " + name + " 的RedisCache实例");
 * 
 * Cache c = caches.get(name);
 * 
 * if (c == null) {
 * 
 * redisManager.init();
 * 
 * c = new RedisCache<K, V>(redisManager, keyPrefix);
 * 
 * caches.put(name, c); } return c; }
 * 
 * public RedisManager getRedisManager() { return redisManager; }
 * 
 * public void setRedisManager(RedisManager redisManager) { this.redisManager =
 * redisManager; }
 * 
 * }
 */