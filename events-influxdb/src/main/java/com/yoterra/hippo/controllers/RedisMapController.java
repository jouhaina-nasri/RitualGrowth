package com.yoterra.hippo.controllers;

import com.yoterra.hippo.services.RedisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/redis")
public class RedisMapController {
    private static final String KEY_PROJECTS = "projects";

    @Autowired
    RedisService redisService;

    @Operation(summary = "Get Redis Project Map",
            description = "Get Redis Project Map")

    @RequestMapping(value = "/projects",method = RequestMethod.GET)
    public Set<String> findProjectKey() {
        return redisService.listOfKeys(KEY_PROJECTS);
    }

    @Operation(summary = "Get Redis Cohort Map",
            description = "Get Redis Cohort Map")

    @RequestMapping(value = "/cohorts", method = RequestMethod.GET)
    public Set<String> findCohortKey(@RequestParam(name = "id") @Parameter(description = "Project ID") Long project_id) {
        return redisService.listOfKeys(project_id);
    }

    @Operation(summary = "Get Redis User Map",
            description = "Get Redis User Map")

    @RequestMapping(value = "/users",method = RequestMethod.GET)
    public Set<String> findUsersKey(@RequestParam(name = "projectID") @Parameter(description = "Project ID") Long project_id,
                             @RequestParam(name = "cohortID") @Parameter(description = "Cohort ID") Long cohort_id){
        String hashKey= project_id.toString()+cohort_id.toString();
        return redisService.listOfKeys(hashKey);
    }

    @Operation(summary = "Get pages of one User",
            description = "Get pages of one User")

    @RequestMapping(value = "/pages",method = RequestMethod.GET)
    public Object findPages(@RequestParam(name = "projectID") @Parameter(description = "Project ID") Long project_id,
                                  @RequestParam(name = "cohortID") @Parameter(description = "Cohort ID") Long cohort_id,
                                  @RequestParam(name = "userID") @Parameter(description = "User ID") Long user_id) {
        String hashKey= project_id.toString()+cohort_id.toString();
        return redisService.findById(hashKey,user_id);
    }


}
