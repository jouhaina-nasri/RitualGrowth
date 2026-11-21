package com.yoterra.hippo.search.qbuilders;

import com.yoterra.hippo.jpa.entities.Company;
import com.yoterra.hippo.jpa.entities.users.User;
import com.yoterra.hippo.jpa.queries.QueryBuilder;
import com.yoterra.hippo.jpa.specs.CompanySpecifications;
import com.yoterra.hippo.jpa.specs.UserSpecifications;
import com.yoterra.hippo.search.requests.CompanySearchRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class CompanyQueryBuilder extends QueryBuilder<Company, CompanySearchRequest> {
    @Override
    public Specification<Company> build(CompanySearchRequest req) {

        Specification<Company> spec = Specification.where(null);

        if(StringUtils.isNotBlank(req.getName())){
            spec = spec.and(CompanySpecifications.nameIs(req.getName()));
        }

        if(StringUtils.isNotBlank(req.getNamePrefix())){
            spec = spec.and(CompanySpecifications.nameContains(req.getNamePrefix(),true));
        }

        if(StringUtils.isNotBlank(req.getWebsite())){
            spec = spec.and(CompanySpecifications.websiteIs(req.getWebsite()));
        }

        if(req.getOwnerId() != null){
            spec = spec.and(CompanySpecifications.userIdIs(req.getOwnerId()));
        }


        return null;
    }
}
