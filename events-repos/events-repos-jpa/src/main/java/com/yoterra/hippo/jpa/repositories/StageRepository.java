package com.yoterra.hippo.jpa.repositories;

import com.yoterra.hippo.jpa.entities.Stage;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "stage", path = "stage")
public interface StageRepository extends PagingAndSortingRepository<Stage, Long> {
}
