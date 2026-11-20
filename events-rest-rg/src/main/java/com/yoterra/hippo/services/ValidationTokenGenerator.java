package com.yoterra.hippo.services;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.yoterra.hippo.exceptions.verification.TokenExpiredException;
import com.yoterra.hippo.helpers.HippoCrypter;

@Component
public class ValidationTokenGenerator {
	
	public static class TimestampToken implements Serializable {
		
		private static final long serialVersionUID = 7948305382160316327L;
		
		private final Long ts;
		private final String t;
		
		public static TimestampToken of(String t){
			return of(System.currentTimeMillis(), t);
		}
		
		public static TimestampToken of(Long ts, String t){
			return new TimestampToken(ts, t);
		}

		private TimestampToken(Long ts, String t){
			this.ts = ts;
			this.t = t;
		}

		public Long getTs() {
			return ts;
		}

		public String getT() {
			return t;
		}
		
		public byte [] toBytes(){
			byte[]tarr = t.getBytes(Charset.defaultCharset());
			ByteBuffer bb = ByteBuffer.allocate(Long.BYTES + tarr.length);
			bb.putLong(ts);
			bb.put(tarr);
			return bb.array();
		}
		
		public static TimestampToken fromBytes(byte [] arr){
			ByteBuffer bb = ByteBuffer.wrap(arr);
			byte [] tsarr = new byte [Long.BYTES];
			byte [] tarr = new byte [arr.length - Long.BYTES];
			bb.get(tsarr);
			bb.get(tarr);
			
			long ts = ByteBuffer.wrap(tsarr).getLong();
			String t = new String(tarr, Charset.defaultCharset());
			return TimestampToken.of(ts, t);
		}
		
		public boolean isExpired(long limitInMilis){
			return this.ts + limitInMilis < System.currentTimeMillis();
		}
	}
	
	private String tokeneGenPassword;
	
	@Autowired
	public ValidationTokenGenerator(@Value("${com.hippo.validation.token.generator.password:V2ULirjywDx9RV}") String tgPass){
		this.tokeneGenPassword = tgPass;
	}

	public String generateToken(int lenght) {
		return RandomStringUtils.randomAlphabetic(lenght);
	}
	
	public String wrapWithTimestamp(String token) {
		byte[] bytes = TimestampToken.of(token).toBytes();
		byte[] crypted = HippoCrypter.encrypt(bytes, tokeneGenPassword);
		String encoded = Base64.encodeBase64URLSafeString(crypted);
		return encoded;
	}
	
	public String  unwrapWithTimestamplAndCheck(String wrapped, long expairationPeriodInMilis) throws TokenExpiredException {
		byte[] crypted = Base64.decodeBase64(wrapped);
		byte[] decrypted = HippoCrypter.decrypt(crypted, tokeneGenPassword);
		TimestampToken tt = TimestampToken.fromBytes(decrypted);

		if(tt.isExpired(expairationPeriodInMilis)) {
			throw new TokenExpiredException("Reactivation token has expired!");
		}
		
		return tt.getT();
	}
	
	public static void main(String[] args) throws TokenExpiredException {
		ValidationTokenGenerator g = new ValidationTokenGenerator("123213");
		String t = g.generateToken(32);
		System.out.println("Generated token: " + t);
		String wrapped = g.wrapWithTimestamp(t);
		System.out.println("Wrapped: " + wrapped);
		//		ThreadUtils.sleepIfNoInterrupt(100l);
		String ut = g.unwrapWithTimestamplAndCheck(wrapped, 1000l);
		
		System.out.println("Unwrapped token: " + ut);
	}
}
