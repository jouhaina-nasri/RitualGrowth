package com.yoterra.hippo.services;

import com.yoterra.hippo.jpa.entities.Cohort;
import com.yoterra.hippo.jpa.entities.users.User;
import com.yoterra.hippo.response.ResponseMessage;

import java.util.List;
import java.util.Set;

public interface CohortService {

    public ResponseMessage save();

    public ResponseMessage delete(Long id);

    public ResponseMessage deleteAll();

    public List<Cohort> findAll();

    public Cohort findById(Long id);

    public List<Cohort> findByProjectId(Long idproject);

    public Set<User> findByCohort(Long id);


}
