package com.ftn.sbnz.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.sbnz.dto.ContinuousSensorDTO;
import com.ftn.sbnz.model.models.ContinuousSensor;
import com.ftn.sbnz.service.SensorService;

@RestController
public class SensorController {
	private static Logger log = LoggerFactory.getLogger(SensorController.class);

	@Autowired
	public SensorService sensorService;

	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json")
	public ContinuousSensor addSensor(@RequestParam String sensorType) {

		ContinuousSensorDTO sensor = new ContinuousSensorDTO(sensorType, 30);
		ContinuousSensor newSensor = sensorService.addSensor(sensor);

		log.debug("Added new sensor: " + sensor);

		return newSensor;

	}

	@RequestMapping(value = "/reading", method = RequestMethod.PUT, produces = "application/json")
	public void reading(@RequestParam Long sensorId, @RequestParam String sensorType, @RequestParam double value) {
		sensorService.sensorReading(sensorType, sensorId, value);

		log.debug("Added new sensor reading for sensor with id: " + sensorId);

	}

}
