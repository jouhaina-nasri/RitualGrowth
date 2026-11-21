package com.yoterra.hippo.jpa.repositories;

import com.yoterra.hippo.jpa.entities.RetentionPeriod;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(collectionResourceRel = "retentionPeriod", path = "retention")
public interface RetentionPeriodRepository extends PagingAndSortingRepository<RetentionPeriod, Long> {
}