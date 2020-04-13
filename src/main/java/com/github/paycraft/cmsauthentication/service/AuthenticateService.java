package com.github.paycraft.cmsauthentication.service;

import com.github.paycraft.cmsauthentication.model.Authenticate;

public interface AuthenticateService {
	
	boolean isValidTimestamp(long timestamp);
	
	boolean isValidHeader(Authenticate request, String hmacHeader,long timestamp);
	
	boolean isKeyAlreadyExit(String key);
}
