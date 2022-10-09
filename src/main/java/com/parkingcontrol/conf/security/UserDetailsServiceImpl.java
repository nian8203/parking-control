package com.parkingcontrol.conf.security;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.parkingcontrol.entity.UserModel;
import com.parkingcontrol.repository.IUserRepository;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private IUserRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserModel userModel = repository.findByUserName(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuario "+username+" no encontrado"));
//		return userModel;
		return new User(userModel.getUsername(), userModel.getPassword(), true, true, true, true, userModel.getAuthorities());
	}

}
