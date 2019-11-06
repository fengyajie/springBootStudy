package com.example.configuration.shiro;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;

import com.example.util.SerializeUtils;

public class ShiroRedisCache<K,V> implements Cache<K, V>{

	private RedisTemplate  redisTemplate;
	
	private String prefix = "spring-boot-test"; 
	
	
	public String getPrefix() {
		return prefix+":";
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
    
	public ShiroRedisCache(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public ShiroRedisCache(RedisTemplate redisTemplate, String prefix) {
		this.redisTemplate = redisTemplate;
		this.prefix = prefix;
	}

	@Override
	public void clear() throws CacheException {
		redisTemplate.getConnectionFactory().getConnection().flushDb();
	}

	/**
	 * 获取已缓存的key集合
	 */
	@Override
	public Set<K> keys() {
		byte[] prefixs = (getPrefix()+"*").getBytes();
		Set<byte[]> keys = redisTemplate.keys(prefixs);
		if(null==keys) {
			return null;
		}
		Set<K> keySet = new HashSet<>();
		for(byte[] key:keys) {
			keySet.add((K)key);
		}
		return keySet;
	}

	@Override
	public V remove(K k) throws CacheException {
		if(null == k) {
			return null;
		}
		V v = (V) redisTemplate.opsForValue().get(k);
		redisTemplate.delete(k);
		return v;
	}

	@Override
	public int size() {
		return redisTemplate.getConnectionFactory().getConnection().dbSize().intValue();
	}

	@Override
	public Collection<V> values() {
		Set<K> keys = keys();
		List<V> values = new ArrayList<V>(keys.size());
		for(K k:keys) {
			values.add(get(k));
		}
		return values;
	}

	@Override
	public V get(K k) throws CacheException {
		if(k == null) {
			return null;
		}
		return (V) redisTemplate.opsForValue().get(k);
	}

	@Override
	public V put(K k, V v) throws CacheException {
		redisTemplate.opsForValue().set(k, v);
		return v;
	}

	private byte[] getBytesKey(K k) {
		if(k instanceof String ) {
			String prekey = this.prefix+k;
			return prekey.getBytes();
		}else {
			return SerializeUtils.serialize(k);
		}
	}
	
}
