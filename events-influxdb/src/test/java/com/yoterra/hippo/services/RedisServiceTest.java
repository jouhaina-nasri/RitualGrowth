package com.yoterra.hippo.services;

import com.yoterra.hippo.measurements.load.DeviceInformation;
import com.yoterra.hippo.measurements.load.EventScreenEntity;
import com.yoterra.hippo.measurements.load.NavigationParams;
import com.yoterra.hippo.measurements.load.ScreenInformation;
import com.yoterra.hippo.redisRepositories.RedisRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;

import java.security.NoSuchAlgorithmException;
import java.util.*;

import static com.yoterra.hippo.services.HashService.hash;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import lombok.var;


@ExtendWith(MockitoExtension.class)
class RedisServiceTest {

    @Mock
    RedisRepository redisRepository;

    @Mock
    RedisService redisService;

    @InjectMocks
    RedisServiceImpl redisServiceImpl;
    private static final String KEY_PROJECTS = "projects";
    Set<Long> cohorts = new HashSet<>();
    EventScreenEntity eventScreen = new EventScreenEntity();
    ScreenInformation screenInformation = new ScreenInformation();

    Long projectId, cohortId, userId;
    String screenName, userKey;
    Set<String> pages = new HashSet<>();
    Map<Long, Object> userMap = new HashMap<>();
    Set<Long> users = new HashSet<>();
    Map<Long, Object> projects = new HashMap<>();
    Map<Long, Object> cohortMap = new HashMap<>();

    @BeforeEach
    void setUp() throws NoSuchAlgorithmException {
        DeviceInformation deviceInformation = new DeviceInformation(1665146095463L,
                "iPhone 13", "Apple", "iOS", "15.5");
        screenInformation = new ScreenInformation("CollectionsList", "LIST",
                "COLLECTIONS");
        NavigationParams navigationParams = new NavigationParams("8gmn5vIK6ADGlrE73idx6Q", "DEAL");
        eventScreen = new EventScreenEntity("2", "3", "1.0.0",
                "1.0.29", deviceInformation, "VISITED", "Hippo shopping",
                "HippoApp", "Home-AuMzYCVKCVpBGJ0PKgAOz", screenInformation,
                navigationParams, 1665146095463L, 1665146099087L, 1665146095463L,
                3624, "C87E697A-032E-4570-A239-8B3952848A951665055688669",
                "C87E697A-032E-45");

        projectId = hash(eventScreen.getProjectId());
        cohortId = hash(eventScreen.getAppVersion());
        userId = hash(eventScreen.getUserId());
        screenName = screenInformation.getName();
        userKey = projectId.toString() + cohortId.toString();
        pages.add(screenName);
        userMap.put(userId, pages);
        users.add(userId);
        cohorts.add(cohortId);
        projects.put(projectId, cohorts);
        cohortMap.put(cohortId, users);
    }

    @Test
    void testSaveMap() throws NoSuchAlgorithmException {
        doNothing().when(redisRepository).save(KEY_PROJECTS, projectId, cohorts);
        doNothing().when(redisRepository).save(projectId, cohortId, users);

        when(redisService.findAll(KEY_PROJECTS)).thenReturn(projects);
        when(redisService.findAll(projectId)).thenReturn(cohortMap);
        when(redisService.findAll(userKey)).thenReturn(userMap);

        redisServiceImpl.saveMap(eventScreen);

        assertTrue(redisService.findAll(KEY_PROJECTS).containsKey(projectId));
        assertTrue(redisService.findAll(KEY_PROJECTS).get(projectId).toString().contains(cohortId.toString()));
        assertTrue(redisService.findAll(projectId).containsKey(cohortId));
        assertTrue(redisService.findAll(projectId).get(cohortId).toString().contains(userId.toString()));
        assertTrue(redisService.findAll(userKey).containsKey(userId));
        assertTrue(redisService.findAll(userKey).get(userId).toString().contains(screenName));
    }

    @Test
    public void updateMapUserTest() {
        doNothing().when(redisRepository).save(userKey, userId, pages);

        redisServiceImpl.updateMapUser(projectId, cohortId, userId, screenName);

        when(redisService.findAll(userKey)).thenReturn(userMap);

        verify(redisRepository, times(1)).save(userKey, userId, pages);
        assertEquals(redisService.findAll(userKey), userMap);
    }

    @Test
    public void updateMapCohortTest() {
        doNothing().when(redisRepository).save(projectId, cohortId, users);

        redisServiceImpl.updateMapCohort(projectId, users, cohortId, userId, screenName);

        verify(redisRepository, times(1)).save(projectId, cohortId, users);

        when(redisService.findAll(userKey)).thenReturn(userMap);

        when(redisService.findAll(projectId)).thenReturn(cohortMap);

        assertTrue(String.valueOf(redisService.findAll(userKey).get(userId)).contains(screenName));
        assertTrue(String.valueOf(redisService.findAll(projectId).get(cohortId)).contains(userId.toString()));
    }

    @Test
    public void updateValueProject() {
        doNothing().when(redisRepository).save(projectId, cohortId, users);

        when(redisService.findAll(projectId)).thenReturn(cohortMap);
        when(redisService.findAll(userKey)).thenReturn(userMap);
        redisServiceImpl.updateValueProject(projectId, cohortId, userId, screenName);

        verify(redisRepository, times(1)).save(projectId, cohortId, users);

        assertTrue(redisService.findAll(projectId).containsKey(cohortId));
        assertTrue(String.valueOf(redisService.findAll(projectId).get(cohortId)).contains(userId.toString()));
        assertTrue(redisService.findAll(userKey).containsKey(userId));
        assertTrue(String.valueOf(redisService.findAll(userKey).get(userId)).contains(screenName));
    }

    @Test
    public void createValueProject() {
        doNothing().when(redisRepository).save(KEY_PROJECTS, projectId, cohorts);

        doNothing().when(redisRepository).save(projectId, cohortId, users);

        when(redisService.findAll(KEY_PROJECTS)).thenReturn(projects);
        when(redisService.findAll(projectId)).thenReturn(cohortMap);
        when(redisService.findAll(userKey)).thenReturn(userMap);

        redisServiceImpl.createValueProject(projectId, cohortId, userId, screenName);

        verify(redisRepository, times(1)).save(projectId, cohortId, users);

        assertTrue(redisService.findAll(KEY_PROJECTS).containsKey(projectId));
        assertTrue(redisService.findAll(KEY_PROJECTS).get(projectId).toString().contains(cohortId.toString()));
        assertTrue(redisService.findAll(projectId).containsKey(cohortId));
        assertTrue(redisService.findAll(projectId).get(cohortId).toString().contains(userId.toString()));
        assertTrue(redisService.findAll(userKey).containsKey(userId));
        assertTrue(redisService.findAll(userKey).get(userId).toString().contains(screenName));
    }

    @Test
    public void hierarchicalMaps() {
        doNothing().when(redisRepository).save(KEY_PROJECTS, projectId, cohorts);
        doNothing().when(redisRepository).save(projectId, cohortId, users);
        doNothing().when(redisRepository).save(userKey, userId, pages);

        redisServiceImpl.hierarchicalMaps(projectId, cohortId, userId, screenName);

        verify(redisRepository, times(1)).save(KEY_PROJECTS, projectId, cohorts);
        verify(redisRepository, times(1)).save(projectId, cohortId, users);
        verify(redisRepository, times(1)).save(userKey, userId, pages);


        when(redisService.findAll(KEY_PROJECTS)).thenReturn(projects);
        when(redisService.findAll(projectId)).thenReturn(cohortMap);
        when(redisService.findAll(userKey)).thenReturn(userMap);

        assertTrue(redisService.findAll(KEY_PROJECTS).containsKey(projectId));
        assertTrue(redisService.findAll(KEY_PROJECTS).get(projectId).toString().contains(cohortId.toString()));
        assertTrue(redisService.findAll(projectId).containsKey(cohortId));
        assertTrue(redisService.findAll(projectId).get(cohortId).toString().contains(userId.toString()));
        assertTrue(redisService.findAll(userKey).containsKey(userId));
        assertTrue(redisService.findAll(userKey).get(userId).toString().contains(screenName));
    }

    @Test
    void findAll() {
        when(redisRepository.findAll(KEY_PROJECTS)).thenReturn(projects);
        Map<Long, Object> result = redisServiceImpl.findAll(KEY_PROJECTS);
        assertEquals(projects, result);
        verify(redisRepository, times(1)).findAll(KEY_PROJECTS);
    }

    @Test
    void findAllNotDuplicates() {
        projects.put(2L, cohorts);
        when(redisRepository.findAll(KEY_PROJECTS)).thenReturn(projects);
        Map<Long, Object> result = redisServiceImpl.findAll(KEY_PROJECTS);
        verify(redisRepository, times(1)).findAll(KEY_PROJECTS);
        List<Long> listOfKeys = new ArrayList<>(result.keySet());
        assertThat(listOfKeys).doesNotHaveDuplicates();
    }

    @Test
    void findById() {
        when(redisRepository.findById(KEY_PROJECTS, projectId)).thenReturn(cohorts);
        final var item = redisServiceImpl.findById(KEY_PROJECTS, projectId);
        assertEquals(cohorts, item);
        verify(redisRepository, times(1)).findById(KEY_PROJECTS, projectId);
    }

    @Test
    void findWithNotExistingId() {
        when(redisRepository.findById(KEY_PROJECTS, projectId)).thenReturn(cohorts);
        final var item = redisServiceImpl.findById(KEY_PROJECTS, projectId);
        assertEquals(cohorts, item);
        final var notExist = redisServiceImpl.findById(KEY_PROJECTS, 1L);
        assertNull(notExist);
    }

    @Test
    void listOfKeys() {
        projects.put(1L, cohorts);
        when(redisRepository.findAll(KEY_PROJECTS)).thenReturn(projects);
        Set<String> keys = redisServiceImpl.listOfKeys(KEY_PROJECTS);
        assertFalse(keys.isEmpty());
        assertThat(keys, hasItems(projectId.toString(), "1"));
        verify(redisRepository, times(1)).findAll(KEY_PROJECTS);
    }

    @Test
    void listOfKeysNotDuplicates() {
        projects.put(1L, this.cohorts);
        when(redisRepository.findAll(KEY_PROJECTS)).thenReturn(projects);
        Set<String> keys = redisServiceImpl.listOfKeys(KEY_PROJECTS);
        assertThat(keys).doesNotHaveDuplicates();
    }
}