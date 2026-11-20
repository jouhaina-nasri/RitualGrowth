package com.yoterra.hippo.jpa.entities.iasc.mcnts;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

/**
 * Defines entities that have some counter(s) that are not directly readable for the corresponding db table (or any related) 
 * but it has to be resolved optionally after reading the record
 * @examples
 * <li> Collection - has likes count, comments count, items count, followers count...   
 * <li> Brand - has likes count, comments count, followers count, ... 
 * Note that product count is not a meta count cause it being resolved/read from the brand index 
 * <li> User - has comments written count, likes made count, collection made, following and followers count, ...
 * <li> ...
 * @author mihajlo
 */
public interface MetacountsAware {

	public Class<? extends MetacountsAware> getMetacountsClass();
	public Integer getMetacount(String name);
	public void setMetacount(String name, int count);
	public Integer deleteMetacount(String name);

	public static <K, N extends Number, M extends MetacountsAware> void mergeMetacounts(Collection<M> metacountsCollection, Map<K, N> countsMap, 
			Function<M, K> keyResolver, String metacountName) {
		mergeMetacounts(metacountsCollection, countsMap, keyResolver, metacountName, 0);
	}
	
	public static <K, N extends Number, M extends MetacountsAware> void mergeMetacounts(Collection<M> metacountsCollection, Map<K, N> countsMap, 
			Function<M, K> keyResolver, String metacountName, int defaultCnt) {
		
		if(metacountsCollection != null) {
			for (M metacounts : metacountsCollection) {
				K key = keyResolver.apply(metacounts);
				N cnt = countsMap.get(key);
				metacounts.setMetacount(metacountName, cnt == null ? 0 : cnt.intValue());
			}
		}
	}
	
	public static <K, N extends Number, M extends MetacountsAware> void mergeMetacounts(Collection<M> metacountsCollection, Function<Collection<M>, Map<K, N>> countsMapGenerator, 
			Function<M, K> keyResolver, String metacountName) {
		mergeMetacounts(metacountsCollection, countsMapGenerator, keyResolver, metacountName, 0);
	}
	
	public static <K, N extends Number, M extends MetacountsAware> void mergeMetacounts(Collection<M> metacountsCollection, Function<Collection<M>, Map<K, N>> countsMapGenerator, 
			Function<M, K> keyResolver, String metacountName, int defaultCnt) {
		
		Map<K, N> countsMap = countsMapGenerator.apply(metacountsCollection);
		mergeMetacounts(metacountsCollection, countsMap, keyResolver, metacountName);
	}
}
