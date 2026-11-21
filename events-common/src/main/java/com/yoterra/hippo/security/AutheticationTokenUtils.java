package com.yoterra.hippo.security;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

public class AutheticationTokenUtils {
	
	public static String extractToken(HttpServletRequest request) {
		String bearerToken = null;
		String authorization = request.getHeader("Authorization");
		if (StringUtils.startsWith(authorization, "Bearer ")) {
			bearerToken = StringUtils.substringAfter(authorization, "Bearer ");
		}
		return bearerToken;
	}

}
