package com.parkingcontrol.service;

import java.util.List;
import java.util.Optional;

import com.parkingcontrol.entity.UserModel;

public interface IUserService {

	public UserModel create(UserModel userModel);
	public List<UserModel> get();
	public Optional<UserModel> buscarPorUserName(String username);
}
