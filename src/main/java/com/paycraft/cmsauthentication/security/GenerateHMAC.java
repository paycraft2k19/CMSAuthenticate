package com.paycraft.cmsauthentication.security;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

@Service
public class GenerateHMAC {

	private static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";

	public String convertTOHmac(String secret, String dataToEncode) {
		Mac sha256;
		try {
			sha256 = Mac.getInstance(HMAC_SHA256_ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException();
		}
		SecretKeySpec keySpec = new SecretKeySpec(secret.getBytes(), HMAC_SHA256_ALGORITHM);
		try {
			sha256.init(keySpec);
		} catch (InvalidKeyException e) {
			throw new RuntimeException();
		}
		return Base64.encodeBase64String(sha256.doFinal(dataToEncode.getBytes()));
	}
}
