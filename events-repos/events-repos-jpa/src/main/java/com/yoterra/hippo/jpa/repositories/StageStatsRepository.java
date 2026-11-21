package com.yoterra.hippo.jpa.repositories;


import com.yoterra.hippo.jpa.entities.StageStats;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(collectionResourceRel = "stageStats", path = "stageStats")
public interface StageStatsRepository extends PagingAndSortingRepository<StageStats, Long> {
}
