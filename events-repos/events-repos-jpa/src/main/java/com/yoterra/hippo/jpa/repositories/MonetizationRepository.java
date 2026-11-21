package com.yoterra.hippo.jpa.repositories;


import com.yoterra.hippo.jpa.entities.Monetization;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(collectionResourceRel = "monetization", path = "monetization")
public interface MonetizationRepository extends PagingAndSortingRepository<Monetization, Long> {
}