package com.yoterra.hippo.helpers;

import java.nio.charset.Charset;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.yoterra.commons.DigestUtils;

public class HippoCrypter {
	
	private static final Charset CHARSET = Charset.forName("utf8");

	public static byte[] encryptText(String text, String pass) {
		byte[] utf8 = textToBytes(text);
		return encrypt(utf8, pass);
	}

	public static String decryptToText(byte[] enc, String pass) {
		byte[] content = decrypt(enc, pass);
		return bytesToText(content);
	}
	
	public static byte[] encrypt(byte [] content, String pass) {
		try {
			SecretKey key = passToAESKey(pass);
			
			Cipher ecipher = Cipher.getInstance("AES");
			ecipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] enc = ecipher.doFinal(content);
			return enc;
		}catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}
	
	public static byte [] decrypt(byte[] enc, String pass) {
		try {
			SecretKey key = passToAESKey(pass);
			
			Cipher dcipher = Cipher.getInstance("AES");
			dcipher.init(Cipher.DECRYPT_MODE, key);
			byte[] content = dcipher.doFinal(enc);
			return content;
		}catch (Exception e) {
			throw new IllegalStateException("Probably the password is incorrect!", e);
		}
	}
	
	// =====================================================
	// ---------- TEXT <-> BYTE [] conversion --------------
	// =====================================================
	
	private static byte[] textToBytes(String text) {
		return text.getBytes(CHARSET);
	}
	
	private static String bytesToText(byte[] content) {
		return new String(content, CHARSET);
	}
	
	// -----------------------------------------------------
	
	private static SecretKey passToAESKey(String pass) {
		byte[] md5 = DigestUtils.md5(pass);					// md5 is used cause it generates a 16 bytes array - it has to be 16 bytes (128 bits)
		SecretKey key = new SecretKeySpec(md5, "AES");
		return key;
	}

	public static void main(String[] args) {
		byte[] crypted = encryptText("abc", "123");
		String decrypted = decryptToText(crypted, "123");
		System.out.println(decrypted);
	}

}
