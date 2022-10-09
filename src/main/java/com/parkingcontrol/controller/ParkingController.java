package com.parkingcontrol.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.parkingcontrol.dtos.ParkingSpotDto;
import com.parkingcontrol.entity.ParkingSpot;
import com.parkingcontrol.servce.impl.ParkingServiceImpl;

@RestController
@RequestMapping("/parking-spot")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ParkingController {

	Map<String, Object> response = new HashMap<>();

	@Autowired
	private ParkingServiceImpl service;

	@PostMapping("/create")
	public ResponseEntity<?> create(@Valid @RequestBody ParkingSpotDto parkingSpotDto, BindingResult result) {

		if (service.findByLicensePlateCar(parkingSpotDto.getLicensePlateCar())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: License plate already in use!");
		}
		if (service.findByParkingSpotNumber(parkingSpotDto.getParkingSpotNumber())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Spot number already in use!");
		}
		if (service.findByApartmentAndBlock(parkingSpotDto.getApartment(), parkingSpotDto.getBlock())) {
			System.out.println("Error findByApartmentAndBlock");
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Apartment or block already in use!");
		}

		var parkingSpot = new ParkingSpot();
		BeanUtils.copyProperties(parkingSpotDto, parkingSpot);
		parkingSpot.setRegistrationDate(LocalDate.now(ZoneId.of("UTC")));

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		try {
			parkingSpot = service.create(parkingSpot);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el registro");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "Usuario creado con éxito!");
		response.put("cliente", parkingSpot);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

	}

	@GetMapping("/get")
	public ResponseEntity<List<ParkingSpot>> getAll() {
		return ResponseEntity.status(HttpStatus.OK).body(service.getAll());
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<?> getById(@PathVariable UUID id) {
		ParkingSpot spot = null;
		Map<String, Object> response = new HashMap<>();

		try {
			spot = service.getById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error de acceso a la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (spot == null) {
			response.put("mensaje", "No se ha encontrado ningun registro con el ID: ".concat(id.toString()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<ParkingSpot>(spot, HttpStatus.OK);
	}
	
	@DeleteMapping("delete/{id}")
	public ResponseEntity<?> delete(@PathVariable UUID id){
		try {
			service.delete(id);
		} catch (DataAccessException e) {
			// TODO: handle exception
			response.put("mensaje", "Error al eliminar los datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "Datos eliminados con exito!");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody ParkingSpotDto parkingSpotDto, @Valid BindingResult result){
		var parkingSpotActual = service.getById(id);
		ParkingSpot datosActualizados = null;
		
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if (parkingSpotActual == null) {
			response.put("mensaje", "No se ha encontrado el registro: '".concat(id.toString()+"'"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		var parkingSpot = new ParkingSpot();
		try {			
			//opcion 1
			
			BeanUtils.copyProperties(parkingSpotDto, parkingSpot);
			parkingSpot.setId(parkingSpotActual.getId());
			parkingSpot.setRegistrationDate(parkingSpotActual.getRegistrationDate());
			datosActualizados = service.create(parkingSpot);
			
			//opcion 2
//			parkingSpotActual.setApartment(parkingSpotDto.getApartment());
//			parkingSpotActual.setBlock(parkingSpotDto.getBlock());
//			parkingSpotActual.setBrandCar(parkingSpotDto.getBrandCar());
//			parkingSpotActual.setColorCar(parkingSpotDto.getColorCar());
//			parkingSpotActual.setLicensePlateCar(parkingSpotDto.getLicensePlateCar());
//			parkingSpotActual.setModelCar(parkingSpotDto.getModelCar());
//			parkingSpotActual.setParkingSpotNumber(parkingSpotDto.getParkingSpotNumber());
//			parkingSpotActual.setResponsibleName(parkingSpotDto.getResponsibleName());
//			
//			datosActualizados = service.create(parkingSpotActual);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error: actualizacion de datos fallida");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "Los datos han sido actualizados con éxito!!");
		response.put("parking", parkingSpot);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<Page<ParkingSpot>>getAllParkingSpot(@PageableDefault(page = 0, size = 4, sort = "id", direction = Sort.Direction.ASC)Pageable pageable){
		return ResponseEntity.status(HttpStatus.OK).body(service.findAll(pageable));
	}	
	
	
}
