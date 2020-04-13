package com.paycraft.cmsauthentication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paycraft.cmsauthentication.model.Device;
import com.paycraft.cmsauthentication.service.DeviceService;

@RestController
@CrossOrigin
@RequestMapping("/devicedata")
public class DeviceController {

	@Autowired
	private DeviceService service;

	@PostMapping("/save")
	public ResponseEntity<String> saveDevice(@RequestBody Device request) {
		service.saveDevice(request);
		return new ResponseEntity<>("Data has been saved", HttpStatus.OK);
	}

	@PostMapping("/devices")
	public ResponseEntity<List<Device>> getDevices() {
		return new ResponseEntity<>(service.getDevices(), HttpStatus.OK);
	}
}
