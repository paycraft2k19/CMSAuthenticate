package com.paycraft.cmsauthentication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paycraft.cmsauthentication.model.Authenticate;
import com.paycraft.cmsauthentication.model.Device;
import com.paycraft.cmsauthentication.model.User;
import com.paycraft.cmsauthentication.redis.CacheService;
import com.paycraft.cmsauthentication.security.GenerateHMAC;
import com.paycraft.cmsauthentication.service.DeviceService;
import com.paycraft.cmsauthentication.service.UserService;

@RestController
@CrossOrigin
@RequestMapping("/device")
public class AuthenticateController {

	private static final long ALLOWED_TIMESTAMP_DIFFERENCE = 600000;

	@Autowired
	private DeviceService deviceService;

	@Autowired
	private UserService userService;

	@Autowired
	private CacheService cache;

	@Autowired
	private GenerateHMAC generateHMAC;

	@Value("${secret}")
	private String secret;

	@PostMapping("/authenticate")
	public ResponseEntity<String> authenticateDevice(@RequestBody Authenticate request,
			@RequestHeader(value = "hmac", required = true) String header) {

		if (!isValidTimestamp(request.getTimestamp())) {
			return new ResponseEntity<>("Timestamp is not allowed", HttpStatus.BAD_REQUEST);
		}

		if (!isValidHeader(request, header)) {
			return new ResponseEntity<>("Invalid header", HttpStatus.BAD_REQUEST);
		}

		Device device = deviceService.findDeviceByID(request.getDeviceId());
		if (isNotRegisteredDevice(request.getDeviceId(), request.getOnBoardingToken(), device)) {
			return new ResponseEntity<>("Unregistered Device", HttpStatus.BAD_REQUEST);
		}

		String existingkey = cache.get(request.getDeviceId());
		if (isKeyAlreadyExit(existingkey)) {
			return new ResponseEntity<>("Key is already generated having key " + existingkey, HttpStatus.OK);
		}

		String key = deviceService.generateKey(device);
		return new ResponseEntity<>(key, HttpStatus.OK);
	}

	@PostMapping("/invalidate")
	public ResponseEntity<String> invalidateDevice(@RequestBody User user, @RequestParam String deviceId) {

		if (!userService.isValidUser(user)) {
			return new ResponseEntity<>("Invalid Username/Password", HttpStatus.OK);
		}
		cache.delete(deviceId);
		deviceService.removeDevice(deviceId);
		return new ResponseEntity<>("Device invalidation successfull", HttpStatus.OK);
	}

	@PostMapping("/revoke")
	public ResponseEntity<String> revokeKey(@RequestBody User user, @RequestParam String deviceId) {
		if (userService.isValidUser(user)) {
			cache.delete(deviceId);
			return new ResponseEntity<>("Key has been removed", HttpStatus.OK);
		}
		return new ResponseEntity<>("Provide valid username and password", HttpStatus.BAD_REQUEST);
	}

	private boolean isNotRegisteredDevice(String deviceId, String onBoardingToken, Device device) {
		return !(device.getDeviceId().equals(deviceId) && device.getOnBoardingToken().equals(onBoardingToken));
	}

	private boolean isKeyAlreadyExit(String key) {
		return key != null;
	}

	private boolean isValidHeader(Authenticate request, String hmacHeader) {
		String contentToEncode = request.getDeviceId() + request.getOnBoardingToken() + request.getTimestamp();
		String hmac = generateHMAC.convertTOHmac(secret, contentToEncode);
		String requestHmac = hmacHeader;
		return hmac.equals(requestHmac);
	}

	private boolean isValidTimestamp(long timestamp) {
		long currentTimestamp = System.currentTimeMillis();
		long lowerTimestamp = currentTimestamp - ALLOWED_TIMESTAMP_DIFFERENCE;
		long upperTimestamp = currentTimestamp + ALLOWED_TIMESTAMP_DIFFERENCE;
		return (timestamp >= lowerTimestamp) && (timestamp <= upperTimestamp);
	}
}
