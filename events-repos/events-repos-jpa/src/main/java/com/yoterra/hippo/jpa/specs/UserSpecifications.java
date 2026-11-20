package com.yoterra.hippo.jpa.specs;

import java.util.Collection;

import org.springframework.data.jpa.domain.Specification;

import com.yoterra.hippo.jpa.entities.users.User;
import com.yoterra.hippo.jpa.entities.users.User_;

public class UserSpecifications extends SpecificationsRoot {
	
	public static Specification<User> fullNameIs(String q) {
		return equal(User_.fullName, q);
	}
	
	public static Specification<User> fullNameContains(String q, boolean ignoreCase) {
		return contains(User_.fullName, q, ignoreCase);
	}
	
	public static Specification<User> fullNamePrefixIs(String q, boolean ignoreCase) {
		return prefixIs(User_.fullName, q, ignoreCase);
	}
	
	public static Specification<User> emailIs(String q) {
		return equal(User_.email, q);
	}
	
	public static Specification<User> emailIn(Collection<String> emails) {
		return in(User_.email,emails);
	}
}
