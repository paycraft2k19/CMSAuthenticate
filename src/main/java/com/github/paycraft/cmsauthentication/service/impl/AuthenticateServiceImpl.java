package com.github.paycraft.cmsauthentication.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.github.paycraft.cmsauthentication.model.Authenticate;
import com.github.paycraft.cmsauthentication.security.GenerateHMAC;
import com.github.paycraft.cmsauthentication.service.AuthenticateService;

public class AuthenticateServiceImpl implements AuthenticateService {

	private static final long ALLOWED_TIMESTAMP_DIFFERENCE = 600000;

	@Value("${secret}")
	private String secret;

	@Autowired
	private GenerateHMAC generateHMAC;

	@Override
	public boolean isValidTimestamp(long timestamp) {
		long currentTimestamp = System.currentTimeMillis();
		long lowerTimestamp = currentTimestamp - ALLOWED_TIMESTAMP_DIFFERENCE;
		long upperTimestamp = currentTimestamp + ALLOWED_TIMESTAMP_DIFFERENCE;
		return (timestamp >= lowerTimestamp) && (timestamp <= upperTimestamp);
	}

	@Override
	public boolean isValidHeader(Authenticate request, String hmacHeader, long timestamp) {
		String contentToEncode = request.getDeviceId() + request.getOnBoardingToken() + timestamp;
		String hmac = generateHMAC.convertTOHmac(secret, contentToEncode);
		String requestHmac = hmacHeader;
		return hmac.equals(requestHmac);
	}

	@Override
	public boolean isKeyAlreadyExit(String key) {
		return key != null;
	}

}
