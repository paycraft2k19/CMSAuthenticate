package com.paycraft.cmsauthentication.dao;

import java.util.List;
import java.util.Optional;

import com.paycraft.cmsauthentication.model.User;

public interface UserDao {

	Optional<User> saveUser(User user);

	Optional<User> getUserByUserId(String userId);

	Optional<List<User>> getAllUsers();

	void deleteUser(String userId);
}
