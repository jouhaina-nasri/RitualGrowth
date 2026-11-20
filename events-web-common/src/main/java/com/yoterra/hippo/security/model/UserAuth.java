package com.yoterra.hippo.security.model;

import java.util.Set;

import com.yoterra.hippo.jpa.entities.Privilege;
import com.yoterra.hippo.jpa.entities.users.User;
import com.yoterra.hippo.jpa.entities.users.UserStatus;
import com.yoterra.utils.Opt;

public class UserAuth {

	private final Long id;
	private final Set<UserStatus> statuses;
	private final Set<String> privileges;
	
	public static UserAuth of (User user) {
		return Opt.get(user, UserAuth::new); 
	}
	
	public UserAuth(User user) {
		this.id = user.getId();
		this.statuses = user.getStatuses();
		this.privileges = Opt.strm(user, User::getPrivileges).noNulls().map(Privilege::getName).toSet();
	}

	public Long getId() {
		return id;
	}

	public Set<String> getPrivileges() {
		return privileges;
	}
	
	public Set<UserStatus> getStatuses() {
		return statuses;
	}

	public boolean hasStatus(UserStatus status) {
		return statuses == null ? false : statuses.contains(status);
	}
	
	public boolean hasPriviege(String privilege) {
		return privileges == null ? false : privileges.contains(privilege);
	}
}
