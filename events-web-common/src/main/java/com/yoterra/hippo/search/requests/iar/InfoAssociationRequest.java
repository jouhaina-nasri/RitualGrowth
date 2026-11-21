package com.yoterra.hippo.search.requests.iar;

import org.apache.commons.lang3.BooleanUtils;
import org.springdoc.api.annotations.ParameterObject;

import io.swagger.v3.oas.annotations.Parameter;

@ParameterObject
public class InfoAssociationRequest {
	
//	java.lang.StackOverflowError: null
//		at com.fasterxml.jackson.databind.type.TypeFactory.constructType(TypeFactory.java:698) ~[jackson-databind-2.11.4.jar:2.11.4]
//		at io.swagger.v3.core.util.PrimitiveType.fromType(PrimitiveType.java:347) ~[swagger-core-2.1.7.jar:2.1.7]
//		at org.springdoc.core.MethodParameterPojoExtractor.isSwaggerPrimitiveType(MethodParameterPojoExtractor.java:204) ~[springdoc-openapi-common-1.5.6.jar:1.5.6]
//		at org.springdoc.core.MethodParameterPojoExtractor.lambda$isSimpleType$4(MethodParameterPojoExtractor.java:193) ~[springdoc-openapi-common-1.5.6.jar:1.5.6]
//		at java.util.stream.MatchOps$1MatchSink.accept(MatchOps.java:90) ~[na:1.8.0_292]
//		at java.util.ArrayList$ArrayListSpliterator.tryAdvance(ArrayList.java:1361) ~[na:1.8.0_292]
// 	|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
//	vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
//	public static final InfoAssociationRequest NONE = new InfoAssociationRequest(null, null);
//	public static final InfoAssociationRequest ALL = new InfoAssociationRequest(true, true);
//	public static final InfoAssociationRequest MC = new InfoAssociationRequest(null, true);
//	public static final InfoAssociationRequest UC = new InfoAssociationRequest(true, null);
	
	

	@Parameter(required = false, 
			description = "Associate user context(s) flag. This is a generic parameter and in some contexts might not be applicable.")
	private Boolean usrCtx;	
	
	@Parameter(required = false, 
			description = "Associate meta counts(s) flag. This is a generic parameter and in some contexts might not be applicable.")
	private Boolean metaCnt;

	
	public InfoAssociationRequest() {}
	
	public InfoAssociationRequest(Boolean aUsrCtx, Boolean aMetaCnt) {
		super();
		this.usrCtx = aUsrCtx;
		this.metaCnt = aMetaCnt;
	}

	public Boolean getUsrCtx() {
		return usrCtx;
	}

	public void setUsrCtx(Boolean usrCtx) {
		this.usrCtx = usrCtx;
	}

	public Boolean getMetaCnt() {
		return metaCnt;
	}

	public void setMetaCnt(Boolean metaCnt) {
		this.metaCnt = metaCnt;
	}

	public boolean associateMetacounts() {
		return BooleanUtils.isTrue(getMetaCnt());
	}

	public boolean associateUserContext() {
		return BooleanUtils.isTrue(getUsrCtx());
	}

	public boolean any() {
		return associateMetacounts() || associateUserContext();
	}


}
