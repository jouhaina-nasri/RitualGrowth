package com.yoterra.hippo.services;

import com.yoterra.hippo.measurements.load.EventScreenEntity;

import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Set;

public interface RedisService {

    void saveMap(EventScreenEntity eventScreenMeasurement) throws NoSuchAlgorithmException;
    Map<Long,Object> findAll(Object hashKey);
    Object findById(Object hashKey,Long id);

    Set<String> listOfKeys(Object hashKey);

}