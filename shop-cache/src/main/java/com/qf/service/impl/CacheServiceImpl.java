package com.qf.service.impl;

import com.qf.service.ICacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.xml.bind.ValidationEvent;
import java.util.concurrent.TimeUnit;

/**
 * @version 1.0
 * @auth qk
 * @date 2020-09-14
 */
@Service
public class CacheServiceImpl implements ICacheService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void set(String key, String val, Long exp) throws Exception {
        if (exp!=null){
            redisTemplate.opsForValue().set(key,val,exp, TimeUnit.SECONDS);
        }else {
            redisTemplate.opsForValue().set(key,val); //不设置超时时间
        }
    }

    @Override
    public void del(String key) throws Exception {
        redisTemplate.delete(key);
    }

    @Override
    public String get(String key) throws Exception {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void setnx(String key, String val, Long exp) throws Exception {
        if (exp!=null){
            redisTemplate.opsForValue().setIfAbsent(key,val,exp,TimeUnit.SECONDS);
        }else {
            redisTemplate.opsForValue().setIfAbsent(key,val);

        }
    }

    @Override
    public String getOrderSequence() {
        return redisTemplate.opsForValue().increment("order_sequence").toString();
    }
}
