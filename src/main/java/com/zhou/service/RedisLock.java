package com.zhou.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @program sell
 * @description:
 * @author: 周茜
 * @create: 2019/11/29 12:03
 */
@Component
@Slf4j
public class RedisLock {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 加锁
     * @param key
     * @param value 当前时间+超时时间
     * @return
     */
    public boolean lock(String key, String value) {

        /**
         * SETNX   set if not exists   如果key不存在  0 等同于set   如果key存在  1 什么也不做
         *  使用！SETN加锁
         *
         *  GETSET  先get在set
         */
        //setIfAbsent  如果可以设置 就是锁着了
        if(redisTemplate.opsForValue().setIfAbsent(key, value)) {
            return true;
        }

        //以下解决死锁  key一直存在 但是没有解锁

        //如果多个线程到这个一步currentValue=A   这两个线程的value都是B  其中一个线程拿到锁
        String currentValue = redisTemplate.opsForValue().get(key);

        //如果为空、或者小于当前时间（锁过期）
        if (!StringUtils.isEmpty(currentValue)
                && Long.parseLong(currentValue) < System.currentTimeMillis()) {

            //获取上一个锁的时间，重新设置时间，防止多个线程同时执行redisTemplate.opsForValue().getAndSet(key, value);
            //一个线程改了之后,其他线程就修改不成功啦
            String oldValue = redisTemplate.opsForValue().getAndSet(key, value);
            if (!StringUtils.isEmpty(oldValue) && oldValue.equals(currentValue)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 解锁
     * @param key
     * @param value
     */
    public void unlock(String key, String value) {
        try {
            String currentValue = redisTemplate.opsForValue().get(key);

            if (!StringUtils.isEmpty(currentValue) && currentValue.equals(value)) {
                redisTemplate.opsForValue().getOperations().delete(key);
            }
        }catch (Exception e) {
            log.error("【redis分布式锁】解锁异常, {}", e);
        }
    }

}