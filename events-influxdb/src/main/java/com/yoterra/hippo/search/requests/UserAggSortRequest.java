package com.yoterra.hippo.search.requests;

import org.springdoc.api.annotations.ParameterObject;

import com.yoterra.hippo.req.SortPageParams;
import com.yoterra.hippo.search.requests.iar.InfoAssociationRequest;
import com.yoterra.hippo.search.requests.iar.InfoAssociationRequestProvider;
import com.yoterra.hippo.search.requests.sort.UserAggSort;

@ParameterObject
public class UserAggSortRequest	extends SortPageParams<UserAggSort> implements InfoAssociationRequestProvider {
	
	private InfoAssociationRequest iar;

	@Override
	public InfoAssociationRequest getIar() {
		return iar;
	}

	@Override
	public void setIar(InfoAssociationRequest iar) {
		this.iar = iar;
	}	
}
