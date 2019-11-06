/*
 * package com.example.configuration.shiro;
 * 
 * import java.util.Set;
 * 
 * import org.springframework.beans.factory.annotation.Value;
 * 
 * import redis.clients.jedis.Jedis; import redis.clients.jedis.JedisPool;
 * import redis.clients.jedis.JedisPoolConfig; import
 * redis.clients.jedis.Protocol;
 * 
 * public class RedisManager {
 * 
 * @Value("${spring.redis.host}") private String host = "127.0.0.1";
 * 
 * @Value("${spring.redis.port}") private int port = 6379;
 * 
 * private int expire = 0;
 * 
 * @Value("${spring.redis.timeout}") private int timeout = 0;
 * 
 * @Value("${spring.redis.password}") private String password = "";
 * 
 * @Value("${spring.redis.database}") private int database =
 * Protocol.DEFAULT_DATABASE;
 * 
 * private static JedisPool jedisPool = null;
 * 
 * public RedisManager() {
 * 
 * }
 * 
 *//**
	 * 初始化方法
	 */
/*
 * public void init() { if (jedisPool == null) { if (password != null &&
 * !"".equals(password)) { jedisPool = new JedisPool(new JedisPoolConfig(),
 * host, port, timeout, password, database); } else if (timeout != 0) {
 * jedisPool = new JedisPool(new JedisPoolConfig(), host, port, timeout, null,
 * database); } else { jedisPool = new JedisPool(new JedisPoolConfig(), host,
 * port, Protocol.DEFAULT_TIMEOUT, null, database); } } }
 * 
 *//**
	 * get value from redis
	 */
/*
 * public byte[] get(byte[] key) { byte[] value = null; Jedis jedis =
 * jedisPool.getResource(); try { value = jedis.get(key); } finally { if (jedis
 * != null) { jedis.close(); } } return value; }
 * 
 *//**
	 * set
	 */
/*
 * public byte[] set(byte[] key, byte[] value) { Jedis jedis =
 * jedisPool.getResource(); try { jedis.set(key, value); if (this.expire != 0) {
 * jedis.expire(key, this.expire); } } finally { if (jedis != null) {
 * jedis.close(); } } return value; }
 * 
 *//**
	 * set
	 */
/*
 * public byte[] set(byte[] key, byte[] value, int expire) { Jedis jedis =
 * jedisPool.getResource(); try { jedis.set(key, value); if (expire != 0) {
 * jedis.expire(key, expire); } } finally { if (jedis != null) { jedis.close();
 * } } return value; }
 * 
 *//**
	 * del
	 */
/*
 * public void del(byte[] key) { Jedis jedis = jedisPool.getResource(); try {
 * jedis.del(key); } finally { if (jedis != null) { jedis.close(); } } }
 * 
 *//**
	 * flush
	 */
/*
 * public void flushDB() { Jedis jedis = jedisPool.getResource(); try {
 * jedis.flushDB(); } finally { if (jedis != null) { jedis.close(); } } }
 * 
 *//**
	 * size
	 */
/*
 * public Long dbSize() { Long dbSize = 0L; Jedis jedis =
 * jedisPool.getResource(); try { dbSize = jedis.dbSize(); } finally { if (jedis
 * != null) { jedis.close(); } } return dbSize; }
 * 
 *//**
	 * keys
	 *//*
		 * public Set<byte[]> keys(String pattern) { Set<byte[]> keys = null; Jedis
		 * jedis = jedisPool.getResource(); try { keys = jedis.keys(pattern.getBytes());
		 * } finally { if (jedis != null) { jedis.close(); } } return keys; }
		 * 
		 * public boolean exists(String key){ Jedis jedis = jedisPool.getResource();
		 * return jedis.exists(key); }
		 * 
		 * public void set(String key,String value,int expire){ Jedis jedis =
		 * jedisPool.getResource(); jedis.set(key, value); jedis.expire(key, expire); }
		 * 
		 * public void del(String key){ Jedis jedis = jedisPool.getResource();
		 * jedis.del(key); } public String getHost() { return host; }
		 * 
		 * public void setHost(String host) { this.host = host; }
		 * 
		 * public int getPort() { return port; }
		 * 
		 * public void setPort(int port) { this.port = port; }
		 * 
		 * public int getExpire() { return expire; }
		 * 
		 * public void setExpire(int expire) { this.expire = expire; }
		 * 
		 * public int getTimeout() { return timeout; }
		 * 
		 * public void setTimeout(int timeout) { this.timeout = timeout; }
		 * 
		 * public String getPassword() { return password; }
		 * 
		 * public void setPassword(String password) { this.password = password; }
		 * 
		 * 
		 * }
		 */