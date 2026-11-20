package com.yoterra.hippo.jpa.entities.iasc.mcnts;

import java.util.Map;

/**
 * MetacountsAware extension with default methods used a String->Integer map
 * @author mihajlo
 *
 */
public interface MetacountsMap extends MetacountsAware {

	public Map<String, Integer> metacountsMap();
	
	public default Integer getMetacount(String name) {
		return metacountsMap().get(name);
	}
	
	public default void setMetacount(String name, int count) {
		metacountsMap().put(name, count);
	}
	
	@Override
	public default Integer deleteMetacount(String name) {
		return metacountsMap().remove(name);
	}
}
