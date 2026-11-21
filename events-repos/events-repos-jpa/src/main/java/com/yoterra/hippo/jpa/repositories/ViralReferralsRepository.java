package com.yoterra.hippo.jpa.repositories;

import com.yoterra.hippo.jpa.entities.ViralReferrals;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "ViralReferrals", path = "ViralReferrals")
public interface ViralReferralsRepository extends PagingAndSortingRepository<ViralReferrals, Long> {
}
