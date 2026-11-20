package com.yoterra.hippo.jpa.repositories;

import com.yoterra.hippo.jpa.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("Select u from Project u where u.projectName=?1")
    Project findByProjectName(String projectName);
}
