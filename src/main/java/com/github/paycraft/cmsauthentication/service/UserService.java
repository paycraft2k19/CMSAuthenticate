package com.github.paycraft.cmsauthentication.service;

import java.util.List;

import com.github.paycraft.cmsauthentication.model.User;

public interface UserService {

	User saveUser(User user);

	User findUserByUserName(String userName);
	
	List<User> getAllUser();
	
	void removeUser(String userId);

	boolean isValidUser(User user);
}
