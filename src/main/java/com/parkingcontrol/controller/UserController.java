package com.parkingcontrol.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parkingcontrol.entity.UserModel;
import com.parkingcontrol.servce.impl.UserServiceImpl;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserServiceImpl serviceImpl;
	
	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestBody UserModel userModel){
		var user = serviceImpl.create(userModel);		
		return ResponseEntity.status(HttpStatus.CREATED).body(user);
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<?> getAll(){
		return ResponseEntity.status(HttpStatus.OK).body(serviceImpl.get());
	}
	
	
}
