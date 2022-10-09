package com.parkingcontrol.servce.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parkingcontrol.entity.UserModel;
import com.parkingcontrol.repository.IUserRepository;
import com.parkingcontrol.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private IUserRepository repository;
	
	@Override
	public UserModel create(UserModel userModel) {
		// TODO Auto-generated method stub
		return repository.save(userModel);
	}

	@Override
	public List<UserModel> get() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	@Override
	public Optional<UserModel> buscarPorUserName(String username) {
		// TODO Auto-generated method stub
		return repository.findByUserName(username);
	}

}
