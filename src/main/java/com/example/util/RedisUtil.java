package com.example.util;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class RedisUtil {
	
	@Autowired
	private RedisTemplate<String,Object> redisTemplate;

	
	 /**
     * 写入缓存
     */
    public boolean set(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<String, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 写入缓存设置时效时间
     */
    public boolean set(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<String, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 批量删除对应的value
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 批量删除key
     */
    public void removePattern(final String pattern) {
        Set<String> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0)
            redisTemplate.delete(keys);
    }
    /**
     * 删除对应的value
     */
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }
    /**
     * 判断缓存中是否有对应的value
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }
    /**
     * 读取缓存
     */
    public Object get(final String key) {
        Object result = null;
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }
    /**
     * 哈希 添加
     */
    public void hmSet(String key, Object hashKey, Object value){
        HashOperations<String, Object, Object>  hash = redisTemplate.opsForHash();
        hash.put(key,hashKey,value);
    }

    /**
     * 哈希获取数据
     */
    public Object hmGet(String key, Object hashKey){
        HashOperations<String, Object, Object>  hash = redisTemplate.opsForHash();
        return hash.get(key,hashKey);
    }
    
    /**
     * 哈希获取Map数据
     */
    public Map<Object, Object> hmGetMap(String key){
        HashOperations<String, Object, Object>  hash = redisTemplate.opsForHash();
        return hash.entries(key);
    }
    
    /**
     * 删除hash数据
     */
    public void hmDel(String key,Object hashKey){
        HashOperations<String, Object, Object>  hash = redisTemplate.opsForHash();
        hash.delete(key, hashKey);
    }
    
    
    
    /**
     * 判断缓存中是否有对应的hash数据
     */
    public boolean exists(String key, Object hashKey) {
        return redisTemplate.opsForHash().hasKey(key, hashKey);
    }

    /**
     * 列表添加
     */
    public void lPush(String k,Object v){
        ListOperations<String, Object> list = redisTemplate.opsForList();
        list.rightPush(k,v);
    }

    /**
     * 返回存储在键中的列表的指定元素。偏移开始和停止是基于零的索引，其中0是列表的第一个元素（列表的头部），1是下一个元素
     */
    public List<Object> lRange(String k, long start, long end){
        ListOperations<String, Object> list = redisTemplate.opsForList();
        return list.range(k,start,end);
    }
    
    /**
     * @description 从左到右，删除第一个元素
     * @param key
     */
    public Object lpop(String key){
        ListOperations<String, Object> oper = redisTemplate.opsForList();
        return  oper.leftPop(key);
    }
    
    /**
     * @description 从右到左，删除第一个元素
     * @param key
     */
    public void rpop(String key){
        ListOperations<String, Object> oper = redisTemplate.opsForList();
        oper.rightPop(key);
    }
    
    /**
     * @description 简单的往数组里面添加元素
     * @param key
     * @param value
     */
    public void lPush(String key, String value){
        ListOperations<String, Object> oper = redisTemplate.opsForList();
        oper.rightPush(key, value);
    }
    
    /**
     * @description 简单的往数组里面添加元素
     * @param key
     * @param value
     */
    public void leftPush(String key, String value){
        ListOperations<String, Object> oper = redisTemplate.opsForList();
        oper.leftPush(key, value);
    }
    
    /**
     * 删除列表中第一个遇到的value值
     * @param key
     * @param count
     * @param value
     * @return 返回列表的长度
     */
    public void lRemove(String key, long count, String value){
        ListOperations<String, Object> oper = redisTemplate.opsForList();
        oper.remove(key, count, value).intValue();
    }
    
    /**
     * @description 获取数组长度
     * @param key
     */
    public int lSize(String key){
        ListOperations<String, Object> oper = redisTemplate.opsForList();
        return oper.size(key).intValue();
    }

    /**
     * 集合添加
     */
    public void add(String key,Object value){
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        set.add(key,value);
    }

    /**
     * 集合获取
     */
    public Set<Object> setMembers(String key){
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        return set.members(key);
    }

    /**
     * 有序集合添加
     */
    public void zAdd(String key,Object value,double scoure){
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        zset.add(key,value,scoure);
    }

    /**
     * 有序集合获取
     */
    public Set<Object> rangeByScore(String key,double scoure,double scoure1){
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        return zset.rangeByScore(key, scoure, scoure1);
    }
    
    /**
     * 有序集合获取
     */
    public Set<Object> rangeByScore(String key, double scoure, double scoure1,int offset,int limit) {
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        return zset.rangeByScore(key, scoure, scoure1,offset,limit);
    }

    
	public void hmset(String key, Map<String, String> value) {
		 HashOperations<String, Object, Object>  hash = redisTemplate.opsForHash();
		 hash.putAll(key, value);
	}
	
	public void hincrBy(String key, String value, Long amount) {
		HashOperations<String, Object, Object>  hash = redisTemplate.opsForHash();
		hash.increment(key, value, amount);
	}
}
