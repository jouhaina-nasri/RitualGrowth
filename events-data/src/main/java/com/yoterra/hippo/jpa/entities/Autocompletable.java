package com.yoterra.hippo.jpa.entities;

/**
 * Defines items that might be looked up (by a user) by typing in a few characters in a search box
 * @examples
 * <li> Brands - users might type in a first few letters of the brand name they are looking for 
 * <li> Collection - users might type in a first few letters of the collection name or description they are looking for 
 * <li> ...
 * @author mihajlo
 * @param <ID>
 */
public interface Autocompletable<ID> extends IdEntity<ID> {
	String getDisplayName();
}
