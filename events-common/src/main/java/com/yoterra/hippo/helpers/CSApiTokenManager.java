package com.yoterra.hippo.helpers;

import java.util.Objects;

import org.apache.commons.lang3.Validate;

import org.apache.commons.codec.digest.DigestUtils;

public class CSApiTokenManager {
	
	private final String password;
	private final int delayTolerance;			// in minutes
	
	public CSApiTokenManager(String password, int delayTolerance) {
		super();
		Validate.notBlank(password, "Password is blank");
		Validate.isTrue(delayTolerance > 0, "Delay tolerance is 0 or negative");
		this.password = password;
		this.delayTolerance = delayTolerance;
	}

	public String generateToken() {
		long ts = System.currentTimeMillis();
		return generateToken(ts);
	}

	public boolean validateToken(String token) {
		long ts = System.currentTimeMillis();
		return validateToken(token, ts);
	}
	
	private String generateToken(long ts) {
		long fmTs = floorToMinutes(ts);
		String secret = makeSecret(fmTs);
		String token = DigestUtils.sha1Hex(secret);
		return token;
	}

	private boolean validateToken(String token, long ts) {
		long fmTs = floorToMinutes(ts);
		long [] tsts = generateMinutTimesToTry(fmTs);  
		for (long tst : tsts) {
			String sec = makeSecret(tst);
			String t = DigestUtils.sha1Hex(sec);
			if(Objects.equals(token, t))
				return true;
		}
		return false;
	}
	
	private long[] generateMinutTimesToTry(long fmTs) {
		long [] ms = new long [delayTolerance * 2 + 1];
		ms[0] = fmTs;
		for (int i = 1 ; i <= delayTolerance ; i++ ) {
			ms[2*i-1] = fmTs + i;
			ms[2*i] = fmTs - i;
		}
		return ms;
	}

	private long floorToMinutes(long ts) {
		return ts/(1000*60);
	}
	
	private String makeSecret(long roundSecTs) {
		String secret = roundSecTs + "|" + password;
		return secret;
	}
	
	public static void main(String[] args) {
		CSApiTokenManager m = new CSApiTokenManager("sdkasdk", 2);
		long min = 60000;
		long ts = System.currentTimeMillis();
		
		long genTs = ts;
		String t = m.generateToken(genTs);
		
		for (int i = -4 ; i < 5; i++) {
			long diff = i* min + 1;
			long testTs = ts + diff;
			boolean valid = m.validateToken(t,testTs);
			System.out.println("Gen ts: " + genTs + ", test ts: " + testTs + ", diff: " + diff + ", valid: " + valid);
		}
	}
}
