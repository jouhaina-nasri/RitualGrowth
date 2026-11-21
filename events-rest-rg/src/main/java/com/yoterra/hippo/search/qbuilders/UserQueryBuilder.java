package com.yoterra.hippo.search.qbuilders;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.yoterra.hippo.jpa.entities.users.User;
import com.yoterra.hippo.jpa.queries.QueryBuilder;
import com.yoterra.hippo.jpa.specs.UserSpecifications;
import com.yoterra.hippo.search.requests.UserSearchRequest;

@Component
public class UserQueryBuilder extends QueryBuilder<User, UserSearchRequest>{

	@Override
	public Specification<User> build(UserSearchRequest req) {

		Specification<User> spec = Specification.where(null);

		
		if(StringUtils.isNotBlank(req.getFullNamePrefix())){
			spec = spec.and(UserSpecifications.fullNamePrefixIs(req.getFullNamePrefix(), true));
		}
		
//		if(StringUtils.isNotBlank(req.getFullNamePrefix())){
//			spec = spec.and(UserSpecifications.fullNameContains(req.getFullName(), true));
//		}
		
		if(StringUtils.isNotBlank(req.getKeywords())){
			Specification<User> fnc = UserSpecifications.fullNameContains(req.getKeywords(), true);
			spec = spec.and(fnc);
		}



		return spec;
	}
}
