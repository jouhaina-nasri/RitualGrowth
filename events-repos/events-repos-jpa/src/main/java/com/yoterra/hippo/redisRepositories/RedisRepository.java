package com.yoterra.hippo.redisRepositories;

import java.util.Map;


public interface RedisRepository {

    void save(Object hashKey,Long key, Object map);

    Map<Long, Object> findAll(Object hashKey);

    Object findById(Object hashKey,Long id);


}