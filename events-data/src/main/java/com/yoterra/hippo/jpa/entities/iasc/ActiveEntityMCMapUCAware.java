package com.yoterra.hippo.jpa.entities.iasc;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.*;

import com.yoterra.hippo.jpa.entities.ActiveEntity;
import com.yoterra.hippo.jpa.entities.iasc.mcnts.MetacountsMap;
import com.yoterra.hippo.jpa.entities.iasc.usrctx.UserCtx;
import com.yoterra.hippo.jpa.entities.iasc.usrctx.UserCtxAware;
import lombok.Data;

/**
 * Many entities are UserCtxAware, MetacountsAware and have to extend ActiveEntity at the same time.
 * This abstract class implements the functionality of all of them so the subclasses would not have to duplicated the code (in most of the cases)
 * @author mihajlo
 *
 */

@MappedSuperclass
public abstract class ActiveEntityMCMapUCAware extends ActiveEntity implements MetacountsMap, UserCtxAware{

	@Transient
	private transient Map<String, Integer> metacountsMap = new HashMap<>();
	
	@Override
	public Map<String, Integer> metacountsMap() {
		return metacountsMap;
	}

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
