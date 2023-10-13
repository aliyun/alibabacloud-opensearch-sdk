package com.aliyun.opensearch.util;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.google.common.io.BaseEncoding;

public class CodecUtils {

	/**
	 * Calculates the MD5 digest and returns the value as a 32 character hex string.
	 *
	 * @param data
	 *            Data to digest
	 * @return MD5 digest as a hex string
	 */
	public static String generateMD5(String data) {
		return hashString(data, "MD5");
	}

	public static String generateSHA1(String data) {
		return hashString(data, "SHA-1");
	}

	public static String generateSHA256(String data) {
		return hashString(data, "SHA-256");
	}

	private static String hashString(String data, String algorithm) {
		try {
			MessageDigest digest = MessageDigest.getInstance(algorithm);
			byte[] hashedBytes = digest.digest(data.getBytes("UTF-8"));

			return convertByteArrayToHexString(hashedBytes);
		} catch (Exception ex) {
			throw new RuntimeException("Could not generate hash from String", ex);
		}
	}

	private static String convertByteArrayToHexString(byte[] arrayBytes) {
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < arrayBytes.length; i++) {
			stringBuffer.append(Integer.toString((arrayBytes[i] & 0xff) + 0x100, 16).substring(1));
		}
		return stringBuffer.toString();
	}

	public static String signature(String stringToSign, String secretString) throws GeneralSecurityException,
			UnsupportedEncodingException {
		byte[] keyBytes = secretString.getBytes("UTF-8");
		SecretKey secretKey = new SecretKeySpec(keyBytes, "HmacSHA1");
		Mac mac = Mac.getInstance("HmacSHA1");
		mac.init(secretKey);
		byte[] text = stringToSign.getBytes("UTF-8");
		byte[] doFinalBytes = mac.doFinal(text);
		return BaseEncoding.base64().encode(doFinalBytes);
	}

}
