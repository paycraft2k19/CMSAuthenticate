package com.github.paycraft.cmsauthentication.dao;

import java.util.List;
import java.util.Optional;

import com.github.paycraft.cmsauthentication.model.Device;

public interface DeviceDao {

	Optional<Device> findByID(String deviceId);

	Optional<Device> save(Device device);

	Optional<Device> delete(String deviceId);

	Optional<List<Device>> getAllDevices();
}
