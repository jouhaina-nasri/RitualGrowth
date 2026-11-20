package com.yoterra.hippo.search.requests.iar;

import org.springdoc.api.annotations.ParameterObject;

import com.yoterra.utils.Opt;

@ParameterObject
public class IARProviderImpl implements InfoAssociationRequestProvider{

	private InfoAssociationRequest iar;
	
	@Override
	public InfoAssociationRequest getIar() {
		return Opt.defVal(iar, NONE);
	}
	
	@Override
	public void setIar(InfoAssociationRequest iar) {
		this.iar = iar;
	}
}
