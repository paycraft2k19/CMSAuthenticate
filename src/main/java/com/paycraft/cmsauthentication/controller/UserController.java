package com.paycraft.cmsauthentication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paycraft.cmsauthentication.model.User;
import com.paycraft.cmsauthentication.service.UserService;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService service;

	@PostMapping("/save")
	public ResponseEntity<String> saveUser(@RequestBody User user) {
		service.saveUser(user);
		return new ResponseEntity<>("User is saved", HttpStatus.OK);
	}

	@PostMapping("/users")
	public ResponseEntity<List<User>> getUsers() {
		return new ResponseEntity<>(service.getAllUser(), HttpStatus.OK);
	}

	@PostMapping("/delete")
	public ResponseEntity<String> deleteUser(@RequestParam String userId) {
		service.removeUser(userId);
		return new ResponseEntity<>("Deletion successfull", HttpStatus.OK);
	}
}
