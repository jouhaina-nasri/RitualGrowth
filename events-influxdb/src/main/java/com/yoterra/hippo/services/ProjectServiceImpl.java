package com.yoterra.hippo.services;

import com.yoterra.hippo.jpa.entities.Project;
import com.yoterra.hippo.jpa.repositories.ProjectRepository;
import com.yoterra.hippo.response.ResponseMessage;
import com.yoterra.hippo.validator.ProjectValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public ResponseMessage save(Project project)
    {
        List<String> errors = ProjectValidator.validate(project);
        if(!errors.isEmpty())
        {
            return new ResponseMessage("Project is not valid "+project);
        }
        projectRepository.save(project);
        return new ResponseMessage("Saved successfully");
    }

    @Override
    public ResponseMessage update(Project project) {
        Project exist = projectRepository.findById(project.getId()).orElse(null);
        if (exist == null) {
            return new ResponseMessage("Project doesn't exist!");
        }
        List<String> errors = ProjectValidator.validate(project);
        if(!errors.isEmpty())
        {
            return new ResponseMessage("Project is not valid "+project);
        }
        projectRepository.save(project);
        return new ResponseMessage("Successfully updated.");
    }

    @Override
    public ResponseMessage delete(Long id) {
        projectRepository.deleteById(id);
        return new ResponseMessage("Successfully deleted.");
    }

    @Override
    public ResponseMessage deleteAll() {
        projectRepository.deleteAll();
        return new ResponseMessage("Successfully deleted.");
    }

    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    @Cacheable(cacheNames = "findById", key = "#id")
    public Project findById(Long id) {
        if (id == null) {
            return null;
        }
        Project project = projectRepository.findById(id).orElse(null);

        return project;
    }

    @Override
    public Project findByName(String name) {
        if (name == null) {
            return null;
        }
        Project project = projectRepository.findByProjectName(name);
        return project;
    }

}
