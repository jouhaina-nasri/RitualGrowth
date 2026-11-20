package com.yoterra.hippo.dto;

import com.yoterra.hippo.jpa.entities.users.User;
import com.yoterra.utils.Opt;

public class UserProfileDto extends UserFullDto{

	private final String email;
	
	public static UserProfileDto of (User user) {
		return Opt.get(user, UserProfileDto::new); 
	}
	
	private UserProfileDto(User user) {
		super(user);
		this.email = user.getEmail();
	}
	
	public String getEmail() {
		return email;
	}
}