package com.parkingcontrol.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.parkingcontrol.entity.ParkingSpot;

@Repository
public interface IParkingRepository extends JpaRepository<ParkingSpot, UUID> {

	boolean existsByLicensePlateCar(String licensePlateCar);
	boolean existsByParkingSpotNumber(String parkingSpotNumber);
	boolean existsByApartmentAndBlock(String apartment, String block);
	
}
