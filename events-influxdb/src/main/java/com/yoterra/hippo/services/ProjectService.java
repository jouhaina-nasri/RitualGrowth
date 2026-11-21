package com.yoterra.hippo.services;

import com.yoterra.hippo.jpa.entities.Project;
import com.yoterra.hippo.response.ResponseMessage;

import java.util.List;

public interface ProjectService {

    public ResponseMessage save(Project project);

    public ResponseMessage update(Project project);

    public ResponseMessage delete(Long id);

    public ResponseMessage deleteAll();

    public List<Project> findAll();

    public Project findById(Long id);

    public Project findByName(String name);
}
