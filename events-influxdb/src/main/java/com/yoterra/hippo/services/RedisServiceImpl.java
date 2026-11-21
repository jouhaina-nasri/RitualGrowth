package com.yoterra.hippo.services;

import com.yoterra.hippo.measurements.load.EventScreenEntity;
import com.yoterra.hippo.redisRepositories.RedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.*;

import java.util.*;

import static com.yoterra.hippo.services.HashService.hash;

@Component
public class RedisServiceImpl implements RedisService {
    private static final String KEY_PROJECTS = "projects";
    @Autowired
    RedisRepository redisRepository;


    public void hierarchicalMaps(Long project_id, Long cohort_id, Long user_id, String screenName) {
        Object object = redisRepository.findById(KEY_PROJECTS, project_id);
        Set<Long> map = (Set<Long>) object;
        if (map != null) {
            if (!map.contains(cohort_id)) {
                this.updateValueProject(project_id, cohort_id, user_id, screenName);
                map.add(cohort_id);
                redisRepository.save(KEY_PROJECTS, project_id, map);
            } else {
                Object object1 = redisRepository.findById(project_id, cohort_id);
                Set<Long> users = (Set<Long>) object1;
                if (!users.contains(user_id)) {
                    this.updateMapCohort(project_id, users, cohort_id, user_id, screenName);
                } else {
                    this.updateMapUser(project_id, cohort_id, user_id, screenName);
                }
            }
        } else {
            this.createValueProject(project_id, cohort_id, user_id, screenName);
        }
    }


    @Override
    public void saveMap(EventScreenEntity eventScreenMeasurement) throws NoSuchAlgorithmException {
        String projectId = eventScreenMeasurement.getProjectId();
        String userId = eventScreenMeasurement.getUserId();
        String appVersion = eventScreenMeasurement.getAppVersion();

        Long project_id = hash(projectId);
        Long user_id = hash(userId);
        Long cohort_id = hash(appVersion);

        String screenName = eventScreenMeasurement.getScreenInformation().getName();

        this.hierarchicalMaps(project_id, cohort_id, user_id, screenName);

    }

    @Override
    public Map<Long, Object> findAll(Object hashKey) {
        return redisRepository.findAll(hashKey);
    }

    @Override
    public Object findById(Object hashKey, Long id) {
        return redisRepository.findById(hashKey, id);
    }

    public void createValueProject(Long project_id, Long cohort_id, Long user_id, String screenName) {
        Set<Long> cohorts = new HashSet<>();
        cohorts.add(cohort_id);
        Set<Long> users = new HashSet<>();
        users.add(user_id);
        redisRepository.save(KEY_PROJECTS, project_id, cohorts);
        redisRepository.save(project_id, cohort_id, users);
        this.updateMapUser(project_id, cohort_id, user_id, screenName);
    }

    public void updateValueProject(Long project_id, Long cohort_id, Long user_id, String screenName) {
        Set<Long> users = new HashSet<>();
        users.add(user_id);
        redisRepository.save(project_id, cohort_id, users);
        this.updateMapUser(project_id, cohort_id, user_id, screenName);
    }


    public void updateMapUser(Long project_id, Long cohort_id, Long user_id, String screenName) {
        String hashKey = project_id.toString() + cohort_id.toString();
        Object object = redisRepository.findById(hashKey, user_id);
        Set<String> pages = new HashSet<>();
        if (object != null) {
            pages = (Set<String>) object;
            System.out.println(pages);
        }
        pages.add(screenName);
        redisRepository.save(hashKey, user_id, pages);
    }

    public void updateMapCohort(Long project_id, Set<Long> users, Long cohort_id, Long user_id, String screenName) {
        users.add(user_id);
        redisRepository.save(project_id, cohort_id, users);
        this.updateMapUser(project_id, cohort_id, user_id, screenName);
    }


    @Override
    public Set<String> listOfKeys(Object hashKey) {
        Map<Long, Object> map = this.findAll(hashKey);
        Set<String> key = new HashSet<>();
        for (Map.Entry mapEntry : map.entrySet()) {
            key.add(mapEntry.getKey().toString());
        }
        return key;
    }
}