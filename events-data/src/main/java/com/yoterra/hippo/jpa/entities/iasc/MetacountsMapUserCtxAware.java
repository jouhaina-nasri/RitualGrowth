package com.yoterra.hippo.jpa.entities.iasc;

import java.util.Map;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.google.common.collect.Maps;
import com.yoterra.hippo.jpa.entities.iasc.mcnts.MetacountsMap;
import com.yoterra.hippo.jpa.entities.iasc.usrctx.UserCtx;
import com.yoterra.hippo.jpa.entities.iasc.usrctx.UserCtxAware;

/**
 * Combines MetacountsMap and UserCtxAware to avoid code duplication in subclasses
 * @author mihajlo
 *
 */
@MappedSuperclass
public abstract class MetacountsMapUserCtxAware implements MetacountsMap, UserCtxAware {

	@Transient
	private transient Map<String, Integer> metacountsMap = Maps.newHashMap();
	
	@Override
	public Map<String, Integer> metacountsMap() {
		return metacountsMap;
	}
	
	// ======================================================
	
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
