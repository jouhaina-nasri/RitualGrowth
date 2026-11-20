package com.yoterra.hippo.jpa.specs;

import com.yoterra.hippo.jpa.entities.Company;
import com.yoterra.hippo.jpa.entities.Company_;

import com.yoterra.hippo.jpa.entities.users.User_;
import org.springframework.data.jpa.domain.Specification;

public class CompanySpecifications extends SpecificationsRoot{

    public static Specification<Company> nameIs(String q) {
        return equal(Company_.name, q);
    }

    public static Specification<Company> nameContains(String q, boolean ignoreCase) {
        return contains(Company_.name, q, ignoreCase);
    }
    public static Specification<Company> websiteIs(String q) {
        return equal(Company_.website, q);
    }

    public static Specification<Company> userIdIs(long id) {

        return equal(Company_.owner, User_.id, id);
    }

}
