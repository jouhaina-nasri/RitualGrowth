package com.yoterra.hippo.dto;

import com.yoterra.hippo.jpa.entities.iasc.usrctx.UserCtx;
import com.yoterra.hippo.jpa.entities.iasc.usrctx.UserCtxAware;

public abstract class UserCtxDto {

	protected UserCtx userContext;
	
	public UserCtxDto(UserCtxAware uctxAware) {
		this.userContext = uctxAware.getUserContext();
	}

	public void setUserContext(UserCtx userContext) {
		this.userContext = userContext;
	}

	public UserCtx getUserContext() {
		return userContext;
	}

}
