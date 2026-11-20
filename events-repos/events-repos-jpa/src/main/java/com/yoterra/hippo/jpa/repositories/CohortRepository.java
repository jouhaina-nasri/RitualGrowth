package com.yoterra.hippo.jpa.repositories;


import com.yoterra.hippo.jpa.entities.Cohort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CohortRepository extends JpaRepository<Cohort, Long> {

    @Query("Select u from Cohort u where u.project_id=?1")
    List<Cohort> findByProjectId(Long project_id);

    @Query("Select u from Cohort u where u.version=?1")
    Cohort findByVersion(String version);


}
