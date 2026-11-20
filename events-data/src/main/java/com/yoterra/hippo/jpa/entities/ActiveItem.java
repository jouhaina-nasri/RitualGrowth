package com.yoterra.hippo.jpa.entities;

/**
 * Defines items that can be deactivated/disabled 
 * When a user account gets deactivated by the user himself all related entities have to be disabled. 
 * It should be equivalent to deleting a user account and all related entities, 
 * but with option to reactivate the account and all the related entities back.
 * Every collection and it's items, comments, messages, invitations, likes, ... have to be deactivated.
 * @see
 * @author mihajlo
 */
public interface ActiveItem {

	public boolean isDisabled();
}
