package com.yoterra.hippo.services;


import com.yoterra.hippo.jpa.entities.Cohort;
import com.yoterra.hippo.jpa.entities.Project;
import com.yoterra.hippo.jpa.entities.users.User;
import com.yoterra.hippo.jpa.repositories.CohortRepository;
import com.yoterra.hippo.measurements.repository.EventRepository;
import com.yoterra.hippo.measurements.entities.EventScreenMeasurement;
import com.yoterra.hippo.redisRepositories.RedisRepository;
import com.yoterra.hippo.response.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;


@Service
public class CohortServiceImpl implements CohortService {

    @Autowired
    private CohortRepository cohortRepository;

    @Autowired
    private RedisRepository redisRepository;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Autowired
    private EventRepository eventScreenRepository;

    @Override
    public ResponseMessage delete(Long id) {
        cohortRepository.deleteById(id);
        return new ResponseMessage("Successfully deleted.");
    }

    @Override
    public ResponseMessage deleteAll() {
        cohortRepository.deleteAll();
        return new ResponseMessage("Successfully deleted.");
    }

    @Override
    public List<Cohort> findAll() {
        List<Cohort> cohorts = new ArrayList<Cohort>();
        cohortRepository.findAll().iterator().forEachRemaining(cohorts::add);
        if (cohorts.isEmpty()) {
            return null;
        }
        return cohorts;
    }

    @Override
    public Cohort findById(Long id) {

        return cohortRepository.findById(id).orElse(null);

    }

    @Override
    public List<Cohort> findByProjectId(Long project_id) {
        List<Cohort> cohort = cohortRepository.findByProjectId(project_id);
        return cohort;
    }

    @Override
    public ResponseMessage save() {
    /*
        List result = redisRepository.findAll();

        for (int i = 0; i < result.size(); i++) {
            Map<Long, Map<Long, Set<Long>>> mapping = (Map<Long, Map<Long, Set<Long>>>) result.get(i);

            for (Map.Entry mapEntry : mapping.entrySet()) {

                Project project = new Project();
                project = projectService.findById((long) mapEntry.getKey());
                String version = this.findVersionByProjectName(project.getProjectName());

                Cohort existing = this.findByVersion(version);
                Map<Long, Set<Long>> map = (Map<Long, Set<Long>>) mapEntry.getValue();
                if (existing == null) {
                    Cohort cohort = new Cohort();
                    cohort.setVersion(version);
                    cohort.setCohortName("Cohort " + version);
                    cohort.setStartDate(Timestamp.from(Instant.now()));
                    cohort.setProject(project);
                    cohort.setProject_id(project.getId());

                    for (Map.Entry mapEntry1 : map.entrySet()) {
                        Set<User> usersList = new HashSet<>();
                        usersList = this.usersGroup(mapEntry1);
                        cohort.setId((long) mapEntry1.getKey());
                        cohort.setUsers(usersList);
                        cohort.setEndDate(Timestamp.from(Instant.now()));
                        cohortRepository.save(cohort);
                    }
                }

            }
        }*/

        return new ResponseMessage("Saved successfully");
    }


    public String findVersionByProjectName(String projectName) {
        String version = null;
        List<EventScreenMeasurement> eventScreen = eventScreenRepository.findAll();
        for (int i = 0; i < eventScreen.size(); i++) {
            if (eventScreen.get(i).getProjectName().equals(projectName)) {
                version = eventScreen.get(i).getAppVersion();
            }
        }
        return version;
    }


    public Cohort findByVersion(String version) {
        return cohortRepository.findByVersion(version);
    }

    public Set<User> usersGroup(Map.Entry mapEntry) {
        Set<Long> users = (Set<Long>) mapEntry.getValue();
        Set<User> usersList = new HashSet<>();
        for (Long id : users) {
            User user = new User();
            user = userService.findById(id);
            usersList.add(user);
        }
        return usersList;
    }

    @Override
    public Set<User> findByCohort(Long id)
    {
        Set<User> users = null;
        Cohort cohort = cohortRepository.findById(id).orElse(null);
        if(cohort!=null)
        {
            users = cohort.getUsers();
        }

        return users;
    }
}
