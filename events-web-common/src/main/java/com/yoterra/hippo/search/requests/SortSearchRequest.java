package com.yoterra.hippo.search.requests;

import org.springdoc.api.annotations.ParameterObject;

import com.yoterra.hippo.req.SortDef;
import com.yoterra.hippo.req.SortPageParams;
import com.yoterra.hippo.search.requests.iar.InfoAssociationRequest;
import com.yoterra.hippo.search.requests.iar.InfoAssociationRequestProvider;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Parameter;

@ParameterObject
public class SortSearchRequest<T extends Enum<? extends SortDef>> 
		extends SortPageParams<T> 
		implements KeywordsRequest, InfoAssociationRequestProvider {
	
	@Parameter(required = false, description = "Query string")
	private String q;		// 'q' - query
	
	private InfoAssociationRequest iar;

	@Hidden
	@Override
	public String getKeywords() {
		return q;
	}
	
	public String getQ() {		
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}

	@Override
	public InfoAssociationRequest getIar() {
		return iar;
	}

	@Override
	public void setIar(InfoAssociationRequest iar) {
		this.iar = iar;
	}	
}
