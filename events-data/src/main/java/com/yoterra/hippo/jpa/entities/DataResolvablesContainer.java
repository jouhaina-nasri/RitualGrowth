package com.yoterra.hippo.jpa.entities;

/**
 * Defines entities that contain {@link DataResolvable}-s or other {@link DataResolvablesContainer}-s
 * @examples
 * <li> CollectionDeal - contains a Deal
 * <li> CollectionProduct - contains a Product
 * <li> CommentCollectionProduct - contains CollectionProduct as a DataResolvablesContainer
 * <li> ...
 * @author mihajlo
 *
 * @param <ID>
 */
public interface DataResolvablesContainer<ID> extends IdEntity<ID>  {
	
	public default Iterable<DataResolvable<?>> getDataResolvables(){
		return null;
	}
	public default Iterable<DataResolvablesContainer<?>> getOtherContainers(){
		return null;
	}
}
