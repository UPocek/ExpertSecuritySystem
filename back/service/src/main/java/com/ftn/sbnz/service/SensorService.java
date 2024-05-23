package com.ftn.sbnz.service;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.ftn.sbnz.model.events.ContinuousSensorEvent;
import com.ftn.sbnz.model.events.DiscretSensorEvent;
import com.ftn.sbnz.model.models.Camera;
import com.ftn.sbnz.model.models.ContinuousSensor;
import com.ftn.sbnz.model.models.DiscretSensor;
import com.ftn.sbnz.model.models.Room;
import com.ftn.sbnz.model.models.Security;
import com.ftn.sbnz.repository.ICameraRepository;
import com.ftn.sbnz.repository.IContinuousSensorRepository;
import com.ftn.sbnz.repository.IDiscretSensorRepository;
import com.ftn.sbnz.repository.IRoomRepository;
import com.ftn.sbnz.repository.ISecurityRepository;
import com.ftn.sbnz.template.KieSessionTemplates;

@Service
public class SensorService {

    @Autowired
    private IContinuousSensorRepository continuousSensorRepository;

    @Autowired
    private IRoomRepository roomRepository;

    @Autowired
    private IDiscretSensorRepository discretSensorRepository;

    @Autowired
    private ICameraRepository cameraRepository;
    @Autowired
    private ISecurityRepository securityRepository;

    @Autowired
    private KieContainer kieContainer;

    private KieSession kieSession;

    @PostConstruct
    public void initialize() {
        kieSession = kieContainer.newKieSession("sensorsKsession");
        updateSessionWithSensors();
    }

    private void updateSessionWithSensors() {
        List<ContinuousSensor> continuousSensor = continuousSensorRepository.findAll();
        if (!continuousSensor.isEmpty()) {
            KieServices kieServices = KieServices.Factory.get();
            String drlLow = KieSessionTemplates.addSensorLowToSession(continuousSensor);
            String drlMedium = KieSessionTemplates.addSensorMediumToSession(continuousSensor);
            String drlHigh = KieSessionTemplates.addSensorHighToSession(continuousSensor);
            KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
            kieFileSystem.write("src/main/resources/rules/drl/sensor_low.drl", drlLow);
            kieFileSystem.write("src/main/resources/rules/drl/sensor_medium.drl", drlMedium);
            kieFileSystem.write("src/main/resources/rules/drl/sensor_high.drl", drlHigh);

            KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
            kieBuilder.buildAll();
            KieModule kieModule = kieBuilder.getKieModule();

            KieBase updatedKieBase = kieServices.newKieContainer(kieModule.getReleaseId()).getKieBase();

            KieSession newSession = updatedKieBase.newKieSession();

            for (Object fact : kieSession.getObjects()) {
                newSession.insert(fact);
            }

            kieSession.dispose();
            kieSession = newSession;
        }
    }

    public ContinuousSensor addContinuousSensor(String sensorType, Long roomId) {
        Optional<Room> room = roomRepository.findById(roomId);
        if (room.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No room with that id");
        }
        ContinuousSensor sensor = new ContinuousSensor(sensorType, 30, room.get());
        continuousSensorRepository.save(sensor);
        continuousSensorRepository.flush();

        updateSessionWithSensors();

        return sensor;
    }

    public DiscretSensor addDiscretSensor(String sensorType, Long roomId) {
        Optional<Room> room = roomRepository.findById(roomId);
        if (room.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No room with that id");
        }
        DiscretSensor sensor = discretSensorRepository.save(new DiscretSensor(sensorType, 30, room.get()));
        discretSensorRepository.flush();

        return sensor;
    }

    public Camera addCamera(Long roomId) {
        Optional<Room> room = roomRepository.findById(roomId);
        if (room.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No room with that id");
        }
        Camera camera = cameraRepository.save(new Camera(room.get()));
        cameraRepository.flush();
        return camera;
    }

    public Security addSecurity() {
        Security security = securityRepository.save(new Security());
        securityRepository.flush();
        return security;
    }

    public void continuousSensorReading(String sensorType, Long roomId, Long sensorId, double value) {
        ContinuousSensorEvent event = new ContinuousSensorEvent(sensorType, roomId, sensorId, value, 1L);
        kieSession.insert(event);
        int n = kieSession.fireAllRules();
        System.out.println("Number of rules fired: " + n);
        System.out.println("All facts in session:");
        for (Object fact : kieSession.getObjects()) {
            System.out.println(fact);
        }
    }

    public void discretSensorReading(String sensorType, Long roomId, Long sensorId) {
        DiscretSensorEvent event = new DiscretSensorEvent(sensorType, roomId, sensorId);
        kieSession.insert(event);
        int n = kieSession.fireAllRules();
        System.out.println("Number of rules fired: " + n);
        System.out.println("All facts in session:");
        for (Object fact : kieSession.getObjects()) {
            System.out.println(fact);
        }
    }

    @PreDestroy
    public void cleanup() {
        kieSession.dispose();
    }
}
