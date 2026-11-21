package com.yoterra.hippo.search.requests;

import org.springdoc.api.annotations.ParameterObject;

import io.swagger.v3.oas.annotations.Parameter;

@ParameterObject
public class UserSearchRequest extends SearchRequest {

	@Parameter(required = false, description = "Filter by username prefix (case insensitive)")
	private String usernamePrefix;

	@Parameter(required = false, description = "Filter by user full name prefix (case insensitive)")
	private String fullNamePrefix;

	public String getUsernamePrefix() {
		return usernamePrefix;
	}

	public void setUsernamePrefix(String usernamePrefix) {
		this.usernamePrefix = usernamePrefix;
	}

	public String getFullNamePrefix() {
		return fullNamePrefix;
	}

	public void setFullNamePrefix(String fullNamePrefix) {
		this.fullNamePrefix = fullNamePrefix;
	}

	public UserSearchRequest initAutocompleteReq(String q) {
		setUsernamePrefix(q);
		this.setSize(AUTOCOMPLETE_PAGE_SIZE);
		return this;
	}


}

