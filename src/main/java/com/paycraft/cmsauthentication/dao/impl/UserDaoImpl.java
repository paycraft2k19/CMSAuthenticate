package com.paycraft.cmsauthentication.dao.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.paycraft.cmsauthentication.dao.UserDao;
import com.paycraft.cmsauthentication.exception.DaoException;
import com.paycraft.cmsauthentication.model.User;

@Component
public class UserDaoImpl implements UserDao {

	@Value("${mongodb.user.collectionName}")
	private String collectionName;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public Optional<User> saveUser(User user) {
		User adminUser = null;
		try {
			adminUser = mongoTemplate.save(user, collectionName);
		} catch (Exception e) {
			throw new DaoException(e.getMessage());
		}
		return Optional.ofNullable(adminUser);
	}

	@Override
	public Optional<User> getUserByUserId(String userId) {

		User adminUser = null;
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("userName").is(userId));
			List<User> users = mongoTemplate.find(query, User.class);
			if (!users.isEmpty())
				adminUser = users.get(0);
		} catch (Exception e) {
			throw new DaoException("Invalid UserName or Password");
		}
		return Optional.ofNullable(adminUser);
	}

	@Override
	public Optional<List<User>> getAllUsers() {
		try {
			return Optional.ofNullable(mongoTemplate.findAll(User.class));
		} catch (Exception e) {
			throw new DaoException(e.getMessage());
		}
	}

	@Override
	public void deleteUser(String userId) {
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("userName").is(userId));
			mongoTemplate.remove(query, User.class);
		} catch (Exception e) {
			throw new DaoException(e.getMessage());
		}
	}

}
