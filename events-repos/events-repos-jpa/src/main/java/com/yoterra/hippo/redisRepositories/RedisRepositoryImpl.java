package com.yoterra.hippo.redisRepositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RedisRepositoryImpl implements RedisRepository {

    private final RedisTemplate<String, Object> redisTemplate;
    private final HashOperations<Object, Long, Object> hashOperations;

    @Autowired
    public RedisRepositoryImpl(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public void save(Object hashKey,Long key, Object map) {
        hashOperations.put(hashKey, key, map);
    }

    @Override
    public Map<Long, Object> findAll(Object hashKey) {
        return hashOperations.entries(hashKey);
    }

    @Override
    public Object findById(Object hashKey,Long id) {
        return hashOperations.get(hashKey, id);
    }


}