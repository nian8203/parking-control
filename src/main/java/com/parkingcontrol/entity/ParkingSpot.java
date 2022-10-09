package com.parkingcontrol.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;

import org.springframework.stereotype.Service;

@Entity
@Table(name = "parking_spot")
public class ParkingSpot implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	@NotEmpty
	@Column(nullable = false, unique = true, length = 10)
	private String parkingSpotNumber;
	@NotEmpty
	@Column(nullable = false, unique = true, length = 7)
	@NotEmpty
	private String licensePlateCar;
	@NotEmpty
	@Column(nullable = false, length = 70)
	@NotEmpty
	private String brandCar;
	@NotEmpty
	@Column(nullable = false, length = 70)
	@NotEmpty
	private String modelCar;
	@NotEmpty
	@Column(nullable = false, length = 70)
	private String colorCar;
	@Column(nullable = false)
	private LocalDate registrationDate;
	@NotEmpty
	@Column(nullable = false, length = 130)
	private String responsibleName;
	@NotEmpty
	@Column(nullable = false, length = 30)
	private String apartment;
	@NotEmpty
	@Column(nullable = false, length = 30)
	private String block;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getParkingSpotNumber() {
		return parkingSpotNumber;
	}

	public void setParkingSpotNumber(String parkingSpotNumber) {
		this.parkingSpotNumber = parkingSpotNumber;
	}

	public String getLicensePlateCar() {
		return licensePlateCar;
	}

	public void setLicensePlateCar(String licensePlateCar) {
		this.licensePlateCar = licensePlateCar;
	}

	public String getBrandCar() {
		return brandCar;
	}

	public void setBrandCar(String brandCar) {
		this.brandCar = brandCar;
	}

	public String getModelCar() {
		return modelCar;
	}

	public void setModelCar(String modelCar) {
		this.modelCar = modelCar;
	}

	public String getColorCar() {
		return colorCar;
	}

	public void setColorCar(String colorCar) {
		this.colorCar = colorCar;
	}

	public LocalDate getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(LocalDate registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getResponsibleName() {
		return responsibleName;
	}

	public void setResponsibleName(String responsibleName) {
		this.responsibleName = responsibleName;
	}

	public String getApartment() {
		return apartment;
	}

	public void setApartment(String apartment) {
		this.apartment = apartment;
	}

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}

}
