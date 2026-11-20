package com.yoterra.hippo.search.requests;

import org.springdoc.api.annotations.ParameterObject;

import com.yoterra.hippo.req.PageParams;
import com.yoterra.hippo.search.requests.iar.InfoAssociationRequest;
import com.yoterra.hippo.search.requests.iar.InfoAssociationRequestProvider;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Parameter;

@ParameterObject
public abstract class SearchRequest extends PageParams implements KeywordsRequest, InfoAssociationRequestProvider {
	
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

	public InfoAssociationRequest getIar() {
		return iar;
	}

	public void setIar(InfoAssociationRequest iar) {
		this.iar = iar;
	}
}
