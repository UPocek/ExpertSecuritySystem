package com.ftn.sbnz.service;

import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.sbnz.dto.ContinuousSensorDTO;
import com.ftn.sbnz.model.events.ContinuousSensorEvent;
import com.ftn.sbnz.model.models.ContinuousSensor;
import com.ftn.sbnz.repository.IContinuousSensorRepository;

@Service
public class SensorService {
    @Autowired
    private KieSession kieSession;

    @Autowired
    private IContinuousSensorRepository sensorRepository;

    public ContinuousSensor addSensor(ContinuousSensorDTO sensorDTO) {
        ContinuousSensor sensor = new ContinuousSensor(sensorDTO.getType(), sensorDTO.getCheckTime());
        sensorRepository.save(sensor);
        sensorRepository.flush();

        kieSession.insert(sensor);
        int n = kieSession.fireAllRules();
        System.out.println("Number of rules fired: " + n);
        return sensor;

    }

    public void sensorReading(String sensorType, Long sensorId, double value) {
        ContinuousSensorEvent event = new ContinuousSensorEvent(sensorType, sensorId, value, 1L);
        kieSession.insert(event);
        int n = kieSession.fireAllRules();
        System.out.println("Number of rules fired: " + n);
    }
}
