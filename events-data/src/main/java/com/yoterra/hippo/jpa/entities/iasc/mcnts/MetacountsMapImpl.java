package com.yoterra.hippo.jpa.entities.iasc.mcnts;

import java.util.Map;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.google.common.collect.Maps;

/**
 * Actual MetacountsMap implementation
 * @author mihajlo
 *
 */
@MappedSuperclass
public abstract class MetacountsMapImpl implements MetacountsMap{

	@Transient
	private transient Map<String, Integer> metacountsMap = Maps.newHashMap();
	
	@Override
	public Map<String, Integer> metacountsMap() {
		return metacountsMap;
	}
}
