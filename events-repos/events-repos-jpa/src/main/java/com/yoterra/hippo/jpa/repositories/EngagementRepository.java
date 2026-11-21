package com.yoterra.hippo.jpa.repositories;


import com.yoterra.hippo.jpa.entities.Engagement;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(collectionResourceRel = "engagement", path = "engagement")
public interface EngagementRepository extends PagingAndSortingRepository<Engagement, Long> {
}
