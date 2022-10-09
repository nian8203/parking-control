package com.parkingcontrol.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.parkingcontrol.entity.UserModel;

public interface IUserRepository extends JpaRepository<UserModel, UUID> {

	Optional<UserModel> findByUserName(String userName);
}
