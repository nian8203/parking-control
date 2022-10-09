package com.parkingcontrol.servce.impl;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.parkingcontrol.entity.ParkingSpot;
import com.parkingcontrol.repository.IParkingRepository;
import com.parkingcontrol.service.IParkingService;

@Service
public class ParkingServiceImpl implements IParkingService {

	@Autowired
	private IParkingRepository repository;

	@Override
	@Transactional
	public ParkingSpot create(ParkingSpot parkingspot) {
		// TODO Auto-generated method stub
		return repository.save(parkingspot);
	}

	public boolean findByLicensePlateCar(String licensePlateCar) {
		// TODO Auto-generated method stub
		return repository.existsByLicensePlateCar(licensePlateCar);
	}

	@Override
	public List<ParkingSpot> getAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	@Override
	public ParkingSpot getById(UUID id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}

	public boolean findByParkingSpotNumber(String parkingSpotNumber) {
		// TODO Auto-generated method stub
		return repository.existsByParkingSpotNumber(parkingSpotNumber);
	}

	public boolean findByApartmentAndBlock(String apartment, String block) {
		// TODO Auto-generated method stub
		return repository.existsByApartmentAndBlock(apartment, block);
	}

	@Override
	public Boolean buscarPorBloqueYApto(String apartment, String block) {
		// TODO Auto-generated method stub
		return repository.existsByApartmentAndBlock(apartment, block);
	}

	@Override
	public void delete(UUID id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}

	@Override
	public Page<ParkingSpot> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return repository.findAll(pageable);
	}

}
