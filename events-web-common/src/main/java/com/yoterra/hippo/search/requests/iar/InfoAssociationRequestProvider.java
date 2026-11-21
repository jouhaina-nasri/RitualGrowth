package com.yoterra.hippo.search.requests.iar;

import io.swagger.v3.oas.annotations.Parameter;

public interface InfoAssociationRequestProvider{
	
	// Could not put these in InfoAssociationRequest due to a StackOverflowError (see comments in InfoAssociationRequest)
	@Parameter(hidden = true)
	public static final InfoAssociationRequest NONE = new InfoAssociationRequest(null, null);
	@Parameter(hidden = true)
	public static final InfoAssociationRequest ALL = new InfoAssociationRequest(true, true);
	@Parameter(hidden = true)
	public static final InfoAssociationRequest MC = new InfoAssociationRequest(null, true);
	@Parameter(hidden = true)
	public static final InfoAssociationRequest UC = new InfoAssociationRequest(true, null);
	
	public default InfoAssociationRequest getInfoAssociationRequest() {
		return getIar();
	}
	
	public InfoAssociationRequest getIar();
	public void setIar(InfoAssociationRequest iar);
}
