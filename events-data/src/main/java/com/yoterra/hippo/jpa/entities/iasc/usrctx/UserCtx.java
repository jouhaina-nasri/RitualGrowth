package com.yoterra.hippo.jpa.entities.iasc.usrctx;

/**
 * Data holder for any entity that might have any kind of relation with the current (active) user
 * The record can be optionally associated with entities
 * Some fields might not be applicable in all the cases 
 * @examples
 * <li> Collection - can be liked by the active user, can be followed by the active user, can be owned by the active user, 'isInFavorites' is not applicable
 * <li> Brand - 'hasLiked', 'isFollowing' and 'isInFavorites' are applicable, 'owns' is not applicable 
 * @author mihajlo
 *
 */
public class UserCtx {
	
	public final Boolean hasLiked;			// the active user has liked the associated entity
	public final Boolean isFollowing;		// the active user is following the associated entity
	public final Boolean isInFavorites;		// the active user has the associated entity in it's favores list
	public final Boolean owns;				// the active user owns the associated entity

	private final Boolean ownerBlocked;

	private final Boolean ownerMuted;



	public UserCtx(Boolean hasLiked, Boolean isFollowing, Boolean isInFavorites, Boolean owns,Boolean ownerBlocked,Boolean ownerMuted ) {
		super();
		this.hasLiked = hasLiked;
		this.isFollowing = isFollowing;
		this.isInFavorites = isInFavorites;
		this.owns = owns;
		this.ownerBlocked = ownerBlocked;
		this.ownerMuted = ownerMuted;
	}

	public Boolean getHasLiked() {
		return hasLiked;
	}

	public Boolean getIsFollowing() {
		return isFollowing;
	}
	
	public Boolean getIsInFavorites() {
		return isInFavorites;
	}
	
	public Boolean getOwns() {
		return owns;
	}
	public Boolean getIsOwnerBlocked() {
		return ownerBlocked;
	}

	public Boolean getIsOwnerMuted() {
		return ownerMuted;
	}
}
