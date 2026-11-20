package com.yoterra.hippo.controllers;


import com.yoterra.hippo.jpa.entities.Project;
import com.yoterra.hippo.response.ResponseMessage;
import com.yoterra.hippo.services.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Operation(summary = "Get available projects",
            description = "Get all projects.")

    @RequestMapping(method = RequestMethod.GET)
    public List<Project> findAll() {
        return projectService.findAll();
    }

    @Operation(summary = "Get full info of one project",
            description = "Get full info of one project"
    )
    @RequestMapping(value = "/getOne", params = "id", method = RequestMethod.GET)
    public Project getOneProject(@RequestParam(name = "id") @Parameter(description = "Project ID") Long id) {
        return projectService.findById(id);
    }

    @Operation(
            summary = "Add a new project.",
            description = "Add a new project."
    )
    @RequestMapping(method = RequestMethod.POST)
    public ResponseMessage createProject(
            @RequestParam("name") @Parameter(description = "Project name") String projectName,
            @RequestParam("website") @Parameter(description = "Project website") String projectWebsite) {

        Project project = new Project(projectName,projectWebsite);
        return projectService.save(project);
    }


    @Operation(
            summary = "Update one project.",
            description = "Update one project."
    )
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseMessage updateProject(
            @RequestParam("id") @Parameter(description = "Project id") long projectId,
            @RequestParam("name") @Parameter(description = "Project name") String projectName,
            @RequestParam("website") @Parameter(description = "Project website") String projectWebsite) {

        Project project = new Project(projectId,projectName,projectWebsite,null);
        return projectService.update(project);
    }

    @Operation(summary = "Delete an existing project.", description = "Delete an existing project.")
    @RequestMapping(value = "/deleteOne", method = RequestMethod.DELETE)
    public ResponseMessage delete(@RequestParam("id") @Parameter(description = "Project id") long projectId) {

        return projectService.delete(projectId);
    }


    @Operation(summary = "Delete all projects.", description = "Delete all projects.")
    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseMessage delete() {

        return projectService.deleteAll();
    }

}

