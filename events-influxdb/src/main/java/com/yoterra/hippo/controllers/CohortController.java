package com.yoterra.hippo.controllers;

import com.yoterra.hippo.jpa.entities.Cohort;
import com.yoterra.hippo.jpa.entities.users.User;
import com.yoterra.hippo.response.ResponseMessage;
import com.yoterra.hippo.services.CohortService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/cohort")
public class CohortController {

    @Autowired
    private CohortService cohortService;

    @Operation(summary = "Get available cohorts",
            description = "Get all cohorts.")

    @RequestMapping(method = RequestMethod.GET)
    public List<Cohort> findAll() {
        return cohortService.findAll();
    }

    @Operation(summary = "Create cohorts",
            description = "Create cohorts.")

    @RequestMapping(method = RequestMethod.POST)
    public ResponseMessage save() {
        return cohortService.save();
    }

    @Operation(summary = "Get full info of one cohort",
            description = "Get full info of one cohort"
    )
    @RequestMapping(value = "/getOne", method = RequestMethod.GET)
    public Cohort getOneCohort(@RequestParam(name = "idCohort") @Parameter(description = "Cohort ID") Long id) {
        return cohortService.findById(id);
    }

    @Operation(summary = "Search cohort by project ID",
            description = "Search cohort by project ID"
    )
    @RequestMapping(value = "/searchByProject", method = RequestMethod.GET)
    public List<Cohort> searchbyProjectId(@RequestParam(name = "idProject") @Parameter(description = "Project ID") Long id) {
        return cohortService.findByProjectId(id);
    }

    @Operation(summary = "Delete an existing cohort.", description = "Delete an existing cohort.")
    @RequestMapping(value = "/deleteOne", method = RequestMethod.DELETE)
    public ResponseMessage delete(@RequestParam("id") @Parameter(description = "Cohort id") long cohortId) {

        return cohortService.delete(cohortId);
    }


    @Operation(summary = "Delete all cohorts.", description = "Delete all cohorts.")
    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseMessage delete() {

        return cohortService.deleteAll();
    }

    @Operation(summary = "Get the group of users in a specific cohort",
            description = "Get the group of users in a specific cohort")

    @RequestMapping(value = "/searchByCohort",method = RequestMethod.GET)
    public Set<User> findByCohort(@RequestParam(name = "Cohort_id") @Parameter(description = "Cohort ID") Long Cohort_id) {
        return cohortService.findByCohort(Cohort_id);
    }

}
