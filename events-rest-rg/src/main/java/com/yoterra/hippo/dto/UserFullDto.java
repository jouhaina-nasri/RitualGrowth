package com.yoterra.hippo.dto;

import com.yoterra.hippo.jpa.entities.users.User;
import com.yoterra.utils.Opt;

public class UserFullDto extends UserDto{

	
	public static UserFullDto of (User user) {
		return Opt.get(user, UserFullDto::new); 
	}
	
	protected UserFullDto(User user) {
		super(user);
	}

}