package com.yoterra.hippo.jpa.repositories;

import com.yoterra.hippo.jpa.entities.SignupFlow;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "signupFlow", path = "signup")
public interface SignupFlowRepository extends PagingAndSortingRepository<SignupFlow, Long> {
}
