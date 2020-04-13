package com.github.paycraft.cmsauthentication.dao.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.github.paycraft.cmsauthentication.dao.DeviceDao;
import com.github.paycraft.cmsauthentication.exception.DaoException;
import com.github.paycraft.cmsauthentication.model.Device;

@Component
public class DeviceDaoImpl implements DeviceDao {

	@Value("${mongodb.collectionName}")
	private String collectionName;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public Optional<Device> findByID(String deviceId) {
		Device device = null;
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("deviceId").is(deviceId));
			List<Device> devices = mongoTemplate.find(query, Device.class, collectionName);
			if (!devices.isEmpty())
				device = devices.get(0);
		} catch (Exception e) {
			throw new DaoException("Device is not registered");
		}
		return Optional.ofNullable(device);
	}

	@Override
	public Optional<Device> save(Device device) {
		Device savedDevice = null;
		try {
			savedDevice = mongoTemplate.save(device);
		} catch (Exception ex) {
			throw new DaoException(ex.getMessage());
		}
		return Optional.ofNullable(savedDevice);
	}

	@Override
	public Optional<Device> delete(String deviceId) {
		Device device = null;
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("deviceId").is(deviceId));
			device = mongoTemplate.findAndRemove(query, Device.class);
		} catch (Exception e) {
			throw new DaoException(e.getMessage());
		}
		return Optional.ofNullable(device);
	}

	@Override
	public Optional<List<Device>> getAllDevices() {
		try {
			return Optional.ofNullable(mongoTemplate.findAll(Device.class));
		} catch (Exception e) {
			throw new DaoException(e.getMessage());
		}
	
	}
}
