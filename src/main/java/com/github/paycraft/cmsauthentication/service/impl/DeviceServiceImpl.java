package com.github.paycraft.cmsauthentication.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.paycraft.cmsauthentication.dao.DeviceDao;
import com.github.paycraft.cmsauthentication.model.Device;
import com.github.paycraft.cmsauthentication.redis.CacheService;
import com.github.paycraft.cmsauthentication.service.DeviceService;

@Service
public class DeviceServiceImpl implements DeviceService {

	@Autowired
	private DeviceDao deviceDao;

	@Autowired
	private CacheService cache;

	@Override
	public Device findDeviceByID(String deviceId) {
		return deviceDao.findByID(deviceId).orElse(getEmptyDevice());
	}

	@Override
	public Device saveDevice(Device device) {
		return deviceDao.save(device).orElse(getEmptyDevice());
	}

	@Override
	public String generateKey(Device device) {
		String key = device.getDeviceId() + System.currentTimeMillis();
		cache.put(String.valueOf(device.getDeviceId()), key, 1);
		return key;
	}

	@Override
	public Device removeDevice(String deviceId) {
		Device device = deviceDao.delete(deviceId).orElse(getEmptyDevice());
		cache.delete(deviceId);
		return device;
	}

	@Override
	public List<Device> getDevices() {
		return deviceDao.getAllDevices().orElse(new ArrayList<Device>());
	}

	@Override
	public boolean isNotRegisteredDevice(String deviceId, String onBoardingToken) {
		Device device = findDeviceByID(deviceId);
		return !(device.getDeviceId().equals(deviceId) && device.getOnBoardingToken().equals(onBoardingToken));
	}

	private Device getEmptyDevice() {
		return new Device();
	}

}
