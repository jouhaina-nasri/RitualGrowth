package com.yoterra.hippo.jpa.entities.iasc.usrctx;

import javax.persistence.Transient;

public abstract class AbstractUserCtxAware implements UserCtxAware{

	@Transient
	private transient UserCtx userCtx;
	
	@Override
	public UserCtx getUserContext() {
		return userCtx;
	}

	@Override
	public void setUserContext(UserCtx userCtx) {
		this.userCtx = userCtx;
	}
}
