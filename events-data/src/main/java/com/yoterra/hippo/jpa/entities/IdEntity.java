package com.yoterra.hippo.jpa.entities;

/**
 * Defines an entity that has an ID of any type
 * @author mihajlo
 * @param <ID>
 */
public interface IdEntity<ID> {

	ID getId();
}
