package com.yoterra.hippo.jpa.entities;

import com.yoterra.hippo.jpa.entities.users.User;

/**
 * Defines items owned by ONE user.
 * @examples
 * <li>Collections - the creator
 * <li>Comments - the author
 * <li>Like - the one who liked
 * <li>Collection item - the collection creator
 * <li>User - <b>the user himself !</b>
 * <li>Message - the sender 
 * <li> ...
 * @author mihajlo
 */
public interface Owned {

	User getOwner();
}
