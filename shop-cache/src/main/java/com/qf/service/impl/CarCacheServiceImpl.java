package com.qf.service.impl;

import com.qf.service.ICarCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Set;

/**
 * @version 1.0
 * @auth qk
 * @date 2020-09-15
 */
@Service
public class CarCacheServiceImpl implements ICarCacheService {

    @Autowired
    private RedisTemplate redisTemplate;

    @PostConstruct
    public void init(){
        //设置key和value的序列化的方式为字符串
        redisTemplate.setKeySerializer(new StringRedisSerializer()); // key
        redisTemplate.setHashKeySerializer(new StringRedisSerializer()); // filed
        redisTemplate.setHashValueSerializer(new StringRedisSerializer()); //  value

    }

    @Override
    public void put(String key, String gid, String sum) {
        redisTemplate.opsForHash().put(key,gid,sum);
    }

    @Override
    public Boolean hasKey(String key, String gid) {
        return redisTemplate.opsForHash().hasKey(key,gid);
    }

    @Override
    public String get(String key, String gid) {
        Object o = redisTemplate.opsForHash().get(key, gid);
        if (o!=null){
            return o.toString();
        }
        return "";
    }

    @Override
    public Long delete(String key, String gid) {
        return redisTemplate.opsForHash().delete(key,gid);
    }

    @Override
    public Set<String> keys(String key) {
        return redisTemplate.opsForHash().keys(key);
    }
}
