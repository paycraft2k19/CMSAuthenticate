package com.github.paycraft.cmsauthentication.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.paycraft.cmsauthentication.dao.UserDao;
import com.github.paycraft.cmsauthentication.model.User;
import com.github.paycraft.cmsauthentication.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao dao;

	@Override
	public User saveUser(User user) {
		return dao.saveUser(user).orElse(emptyUser());
	}

	@Override
	public User findUserByUserName(String userName) {
		return dao.getUserByUserId(userName).orElse(emptyUser());
	}

	@Override
	public boolean isValidUser(User requestUser) {
		User user = dao.getUserByUserId(requestUser.getUserName()).orElse(emptyUser());
		return user.getPassword().equals(requestUser.getPassword());
	}

	@Override
	public List<User> getAllUser() {
		return dao.getAllUsers().orElse(new ArrayList<User>());
	}

	@Override
	public void removeUser(String userId) {
		dao.deleteUser(userId);
	}

	private User emptyUser() {
		return new User();
	}

}
