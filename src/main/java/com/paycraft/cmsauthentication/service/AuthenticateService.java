package com.paycraft.cmsauthentication.service;

import com.paycraft.cmsauthentication.model.Authenticate;

public interface AuthenticateService {
	
	boolean isValidTimestamp(long timestamp);
	
	boolean isValidHeader(Authenticate request, String hmacHeader,long timestamp);
	
	boolean isKeyAlreadyExit(String key);
}
