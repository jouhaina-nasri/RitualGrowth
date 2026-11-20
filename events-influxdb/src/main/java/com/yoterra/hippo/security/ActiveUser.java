package com.yoterra.hippo.security;

import java.util.Objects;

import org.springframework.security.core.context.SecurityContextHolder;

import com.yoterra.hippo.jpa.entities.users.User;
import com.yoterra.hippo.security.model.AuthToken;
import com.yoterra.hippo.security.model.FirebaseAuthentication;
import com.yoterra.utils.Opt;

public class ActiveUser {
	
	public static boolean is(User usr) {
		return idIs(usr.getId());
	}
	
	public static boolean idIs(long userId) {
		return Objects.equals(getId(), userId);
	}
	
	public static boolean is(User usr, boolean required) {
		return idIs(usr.getId(), required);
	}
	
	public static boolean idIs(long userId, boolean required) {
		User au = get(required);
		return Opt.getOrDef(au, false, User::getId, new Long(userId)::equals);
	}
	
	
	public static Long getId() {
		return get(true).getId();
	}
	
	public static Long getId(boolean required) {
		return Opt.get(get(required), User::getId);
	}
	
	
	public static User get() {
		return get(true);
	}
	
	public static User get(boolean required) {
		User user = Opt.get(getUserPrincipal(false), UserPrincipal::getUser);
		return req(user, required, "No user");
	}
	
	public static UserPrincipal getUserPrincipal() {
		return getUserPrincipal(true);
	}
	
	public static UserPrincipal getUserPrincipal(boolean required) {
		UserPrincipal userPrincipal = null;
		Object principal = getAuthentication().getPrincipal();
		if (principal instanceof UserPrincipal) {
			userPrincipal = (UserPrincipal) principal;
		}
		return req(userPrincipal, required, "No user principal");
	}
	
	public static FirebaseAuthentication getAuthentication() {
		return getAuthentication(true);
	}
	
	public static FirebaseAuthentication getAuthentication(boolean required) {
		FirebaseAuthentication authentication = (FirebaseAuthentication) SecurityContextHolder.getContext().getAuthentication();
		return req(authentication, required, "No authentication");
	}
	
	public static AuthToken getToken() {
		return getToken(true);
	}
	
	public static AuthToken getToken(boolean required) {
		AuthToken authToken = Opt.get(getAuthentication(), FirebaseAuthentication::getToken);
		return req(authToken, required, "No authentication token");
	}
	
	public static void setAuthentication(FirebaseAuthentication authentication) {
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
	
	private static <T> T req(T obj, boolean required, String message) {
		return required ? Opt.req(obj, ()->new IllegalStateException(message)) : obj;
	}
}
