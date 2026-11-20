package com.yoterra.hippo.dto;

import java.util.List;

import com.yoterra.hippo.jpa.entities.Privilege;
import com.yoterra.hippo.jpa.entities.users.User;
import com.yoterra.utils.Opt;

public class UserDto{

	private final Long id;
	private final String fullName;
	private String email;
	private final List<String> privileges;
	
	public static UserDto of (User user) {
		return Opt.get(user, UserDto::new); 
	}
	
	public UserDto(User user) {
		this.id = user.getId();
		this.fullName = user.getFullName();
		this.email = user.getEmail();
		this.privileges = Opt.strm(user, User::getPrivileges).noNulls().map(Privilege::getName).toList();
	}

	public Long getId() {
		return id;
	}

	public String getFullName() {
		return fullName;
	}
	
	public List<String> getPrivileges() {
		return privileges;
	}

	public String getEmail() {
		return email;
	}

}
