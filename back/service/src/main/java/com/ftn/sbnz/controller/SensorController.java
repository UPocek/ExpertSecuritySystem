package com.ftn.sbnz.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.sbnz.model.models.Camera;
import com.ftn.sbnz.model.models.ContinuousSensor;
import com.ftn.sbnz.model.models.DiscretSensor;
import com.ftn.sbnz.model.models.Security;
import com.ftn.sbnz.service.SensorService;

@RestController
@RequestMapping("/api/sensor")
public class SensorController {
	private static Logger log = LoggerFactory.getLogger(SensorController.class);

	@Autowired
	public SensorService sensorService;

	@PostMapping("/continuous")
	public ContinuousSensor addContinuousSensor(@RequestParam String sensorType, @RequestParam Long roomId) {
		ContinuousSensor newSensor = sensorService.addContinuousSensor(sensorType, roomId);

		log.debug("Added new sensor: " + newSensor);

		return newSensor;
	}

	@PostMapping("/discret")
	public DiscretSensor addDiscretSensor(@RequestParam String sensorType, @RequestParam Long roomId) {
		DiscretSensor discretSensor = sensorService.addDiscretSensor(sensorType, roomId);

		log.debug("Added new sensor: " + discretSensor);

		return discretSensor;
	}

	@PostMapping("/camera")
	public Camera addCamera(@RequestParam Long roomId) {
		Camera camera = sensorService.addCamera(roomId);

		log.debug("Added new sensor: " + camera);

		return camera;
	}

	@PostMapping("/security")
	public Security addSecurity() {
		Security security = sensorService.addSecurity();

		log.debug("Added new sensor: " + security);

		return security;
	}

	@PutMapping("/update_config")
	public ContinuousSensor updateConfig(@RequestParam Long sensorId, @RequestParam int criticalLowValue,
			@RequestParam int criticalHighValue) {
		ContinuousSensor sensorUpdated = sensorService.updateConfig(sensorId, criticalLowValue, criticalHighValue);

		log.debug("Added new sensor config for sensor with id: " + sensorId);
		return sensorUpdated;

	}

	@PutMapping("/continuous_reading")
	public void reading(@RequestParam Long roomId, @RequestParam Long sensorId, @RequestParam String sensorType,
			@RequestParam double value) {
		sensorService.continuousSensorReading(sensorType, roomId, sensorId, value);

		log.debug("Added new sensor reading for sensor with id: " + sensorId);

	}

	@PutMapping("/discret_reading")
	public void reading(@RequestParam Long roomId, @RequestParam Long sensorId, @RequestParam String sensorType) {
		sensorService.discretSensorReading(sensorType, roomId, sensorId);

		log.debug("Added new sensor reading for sensor with id: " + sensorId);

	}

}
