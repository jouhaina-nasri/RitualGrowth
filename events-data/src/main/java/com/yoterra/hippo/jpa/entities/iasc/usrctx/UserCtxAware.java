package com.yoterra.hippo.jpa.entities.iasc.usrctx;

/**
 * Defines all the entities that can (optionally) be associated with UserCtx
 * @see {@link UserCtx}
 * @author mihajlo
 */
public interface UserCtxAware {

	UserCtx getUserContext();
	void setUserContext(UserCtx  usrCtx);
	
	Class<? extends UserCtxAware> getUCAwareClass();
}
