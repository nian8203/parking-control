package com.parkingcontrol.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.parkingcontrol.entity.ParkingSpot;

public interface IParkingService {

	public List<ParkingSpot> getAll();
	public Page<ParkingSpot> findAll(Pageable pageable);
	public ParkingSpot getById(UUID id);
	public ParkingSpot create(ParkingSpot parkingspot);
	public void delete(UUID id);
	public Boolean buscarPorBloqueYApto(String apartment, String block);
	
}
