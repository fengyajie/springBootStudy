package com.example.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * @author fyj
 * redis分布式锁---防止定时任务重复执行
 */
@Component
public class RedisLockUtil {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 加锁
     * @param key
     * @param value ----当前时间加超时时间，用来处理异常情况没有删除key
     * @return
     */
    private boolean lock(String key,String value){

        //key不存在设置成功
        if(redisTemplate.opsForValue().setIfAbsent(key,value)){
            return true;
        }

        //key存在，判断是否超时未删除
        String currentValue = redisTemplate.opsForValue().get(key);
        //如果超时
        if(!StringUtils.isEmpty(currentValue) && Long.parseLong(currentValue) < System.currentTimeMillis()){
            //设置并返回原值
             String oldValue = redisTemplate.opsForValue().getAndSet(key,value);

             //该方法不是线程安全，设置成功获取的值和原值相等才会设置成功
             if(!StringUtils.isEmpty(oldValue) && oldValue.equals(currentValue)){
                 return true;
             }
        }
        return false;
    }

    /**
     *
     * @param key
     * @param value
     * @param timeOut 过期时间---秒
     * @param trySeconds  尝试获取锁的秒数
     * @return
     */
    public boolean tryLock(String key,String value,long timeOut,long trySeconds){

        long expire = System.currentTimeMillis() + trySeconds*1000;
        while(System.currentTimeMillis() < expire){
            if(lock(key,value)){
                //设置过期时间
                redisTemplate.expire(key,timeOut, TimeUnit.SECONDS);
                return true;
            }
            try {
                TimeUnit.MILLISECONDS.sleep(1L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return lock(key,value);
    }


    /**
     * 释放锁
     * @param key
     */
    public void unLock(String key){

        try {
            redisTemplate.opsForValue().getOperations().delete(key);
        } catch (Exception e) {
            System.out.println("【redis分布式锁】解锁异常！" + e);
        }

    }

}
