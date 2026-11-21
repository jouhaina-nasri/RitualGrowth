package com.yoterra.hippo.jpa.entities;

import javax.persistence.*;

import lombok.Data;
import org.hibernate.annotations.Where;


/**
 * <p>!!!! VERY IMPORTANT !!!!</p>
 * 
 * The account deactivation feature requires that once a user account gets deactivated, all the related entities have to get deactivated as well.
 * All the collections, collection items, comments, messages, invitations, likes, ... everything! This also includes all the entities related to 
 * first level entities deactivated. All the comments, likes, followers, ... of deactivated collections. It's a "snowball effect".
 * All entities that can be deactivated inherit this abstract class.
 * The idea is to use the long IDs to implement the deactivation functionality.
 * Initially the ID are assigned by the sequence generators. Once an entity gets deactivated the ID should be changed to is's negative value.
 * ID 3 gets changed to ID -3.
 * All entities with negative IDs are deactivated, and therefore been ignored by the entity repositories because of the @Where clause !!!!
 * The problem is most of the entities have to be 'manually' deactivated and is being done in {@code DeactivationProcessor} 
 * @author mihajlo
 * @see
 */

@MappedSuperclass
public abstract class ActiveEntity implements ActiveItem, IdEntity<Long>{

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "Id")
    protected Long id;
	
	public Long getId() {
		return id;
	}
	
	@Override
	public boolean isDisabled() {
		return getId() < 0;
	}
}
