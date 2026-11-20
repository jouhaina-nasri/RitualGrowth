package com.yoterra.hippo.search.requests;

import java.util.Map;

import com.yoterra.hippo.search.requests.sort.CompanySort;
import org.springdoc.api.annotations.ParameterObject;

//import com.google.common.collect.ImmutableMap;
import com.yoterra.data.records.DataLocalityUtils;
//import com.yoterra.data.repos.query.CompanyRequest;

import io.swagger.v3.oas.annotations.Parameter;


@ParameterObject

public class CompanySearchRequest extends SortSearchRequest<CompanySort>{

	private static final String US_LOCALITY = DataLocalityUtils.DEFAULT_LOCALITY;

	@Parameter(required = false, description = "Filter by user Id")
	private Long OwnerId;
	
	@Parameter(required = false, description = "Filter by name (exact match)")
	private String name;
	
	@Parameter(required = false, description = "Filter by name prefix")
	private String namePrefix;

	@Parameter(required = false, description = "Filter by website (exact match)")
	private String website;

	public Long getOwnerId() {
		return OwnerId;
	}

	public void setOwnerId(Long ownerId) {
		OwnerId = ownerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNamePrefix() {
		return namePrefix;
	}

	public void setNamePrefix(String namePrefix) {
		this.namePrefix = namePrefix;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	@SuppressWarnings("unchecked")
	public <S extends SortSearchRequest<CompanySort>> S initAutocompleteReq(String q){
		setNamePrefix(q);
		this.setSize(AUTOCOMPLETE_PAGE_SIZE);
		return (S) this;
	}
}
