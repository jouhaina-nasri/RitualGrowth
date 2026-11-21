package com.yoterra.hippo.jpa;

import com.yoterra.hippo.jpa.entities.DataResolvable;

/**
 * Implemented by classes that are responsible for resolving data of data resolvable entities
 * @author mihajlo
 *
 * @param <T>
 */
public interface IDataResolver <T extends DataResolvable<?>>{
	
	@SuppressWarnings("rawtypes")
	Class<? extends DataResolvable> getRecordClass();
	
	void resolveRecordData(T record);
	
	default void resolveRecordsData(Iterable<T> records) {				// the implementations should optimize this
		for (T r : records) {
			resolveRecordData(r);
		}
	}
}
