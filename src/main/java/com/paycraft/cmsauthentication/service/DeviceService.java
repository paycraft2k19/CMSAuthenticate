package com.paycraft.cmsauthentication.service;

import java.util.List;

import com.paycraft.cmsauthentication.model.Device;

public interface DeviceService {

	Device findDeviceByID(String deviceId);

	Device saveDevice(Device device);

	Device removeDevice(String deviceId);

	String generateKey(Device device);

	List<Device> getDevices();

	boolean isNotRegisteredDevice(String deviceId, String onBoardingToken);
}
