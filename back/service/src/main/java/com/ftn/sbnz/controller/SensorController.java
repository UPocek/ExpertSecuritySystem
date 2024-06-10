package com.ftn.sbnz.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.sbnz.dtos.SensorDTO;
import com.ftn.sbnz.model.models.ContinuousSensor;
import com.ftn.sbnz.service.SensorService;

@RestController
@RequestMapping("/api/sensor")
public class SensorController {
	private static Logger log = LoggerFactory.getLogger(SensorController.class);

	@Autowired
	public SensorService sensorService;

	@GetMapping("/all")
	public List<SensorDTO> getAllSensors(@RequestParam Long buildingId) {
		return sensorService.getAllSensors(buildingId);
	}

	@GetMapping("/continuous")
	public List<SensorDTO> getContinuousSensors(@RequestParam Long buildingId) {
		return sensorService.getContinuousSensor(buildingId);
	}

	@GetMapping("/discrete")
	public List<SensorDTO> getDiscreteSensors(@RequestParam Long buildingId) {
		return sensorService.getDiscreteSensors(buildingId);
	}

	@GetMapping("/camera")
	public List<SensorDTO> getCameraSensors(@RequestParam Long buildingId) {
		return sensorService.getCameraSensors(buildingId);
	}

	@PostMapping("/continuous")
	public SensorDTO addContinuousSensor(@RequestParam String sensorType, @RequestParam Long roomId) {
		return sensorService.addContinuousSensor(sensorType, roomId);
	}

	@PostMapping("/discret")
	public SensorDTO addDiscretSensor(@RequestParam String sensorType, @RequestParam Long roomId) {
		return sensorService.addDiscretSensor(sensorType, roomId);
	}

	@PostMapping("/camera")
	public SensorDTO addCamera(@RequestParam Long roomId) {
		return sensorService.addCamera(roomId);

	}

	@PostMapping("/security")
	public SensorDTO addSecurity(@RequestParam Long buildingId) {
		return sensorService.addSecurity(buildingId);
	}

	@PutMapping("/update_config")
	public ContinuousSensor updateConfig(@RequestParam Long sensorId, @RequestParam int criticalLowValue,
			@RequestParam int criticalHighValue) {
		ContinuousSensor sensorUpdated = sensorService.updateConfig(sensorId, criticalLowValue, criticalHighValue);

		log.debug("Added new sensor config for sensor with id: " + sensorId);
		return sensorUpdated;

	}

	@PutMapping("/continuous_reading")
	public void reading(@RequestParam Long sensorId,
			@RequestParam double value) {
		sensorService.continuousSensorReading(sensorId, value);

		log.debug("Added new sensor reading for sensor with id: " + sensorId);

	}

	@PutMapping("/discret_reading")
	public void reading(@RequestParam Long sensorId) {
		sensorService.discretSensorReading(sensorId);

		log.debug("Added new sensor reading for sensor with id: " + sensorId);

	}

	@PutMapping("/camera_reading")
	public void cameraReading(@RequestParam String type, @RequestParam Long sensorId) {
		sensorService.cameraSensorReading(type, sensorId);

		log.debug("Added new camera reading for sensor with id: " + sensorId);

	}

	@PutMapping("/security_reading")
	public void securityReading(@RequestParam String type, @RequestParam Long sensorId) {
		sensorService.securitySensorReading(type, sensorId);
		log.debug("Added new camera reading for sensor with id: " + sensorId);

	}
}
