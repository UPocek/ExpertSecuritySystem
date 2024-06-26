package com.ftn.sbnz.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.drools.core.ClassObjectFilter;

import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ftn.sbnz.dtos.SensorDTO;
import com.ftn.sbnz.managers.SessionManager;
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
    private SessionManager sessionManager;

    public SensorDTO addContinuousSensor(String sensorType, Long roomId) {
        Optional<Room> room = roomRepository.findById(roomId);
        if (room.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No room with that id");
        }
        ContinuousSensor sensor = new ContinuousSensor(sensorType, room.get());
        continuousSensorRepository.save(sensor);
        continuousSensorRepository.flush();

        sessionManager.updateSecuritySession();

        return new SensorDTO(sensor.getId(), sensor.getType(), sensor.getRoom().getId(),
                sensor.getConfig().getCriticalLowValue(), sensor.getConfig().getCriticalHighValue(), false, null, null);
    }

    public ContinuousSensor updateConfig(Long sensorId, int criticalLowValue, int criticalHighValue) {
        Optional<ContinuousSensor> sensor = continuousSensorRepository.findById(sensorId);
        if (sensor.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No sensor with that id");
        }

        ContinuousSensor sensorFounded = sensor.get();
        sensorFounded.getConfig().setCriticalLowValue(criticalLowValue);
        sensorFounded.getConfig().setCriticalHighValue(criticalHighValue);
        continuousSensorRepository.save(sensorFounded);
        continuousSensorRepository.flush();

        sessionManager.updateSecuritySession();

        return sensorFounded;
    }

    public SensorDTO addDiscretSensor(String sensorType, Long roomId) {
        Optional<Room> room = roomRepository.findById(roomId);
        if (room.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No room with that id");
        }
        DiscretSensor sensor = discretSensorRepository.save(new DiscretSensor(sensorType, room.get()));
        discretSensorRepository.flush();

        return new SensorDTO(sensor.getId(), sensor.getType(), sensor.getRoom().getId(),
                -1, -1, false, null, null);
    }

    public SensorDTO addCamera(Long roomId) {
        Optional<Room> room = roomRepository.findById(roomId);
        if (room.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No room with that id");
        }
        Camera camera = cameraRepository.save(new Camera(room.get()));
        cameraRepository.flush();
        return new SensorDTO(camera.getId(), "camera", camera.getRoom().getId(),
                -1, -1, camera.isOn(), null, null);
    }

    public SensorDTO addSecurity(Long buildingId) {
        Security security = securityRepository.save(new Security(buildingId));
        securityRepository.flush();
        return new SensorDTO(security.getId(), "security", null,
                -1, -1, false, security.getCurrentRoomId(), security.getBuildingId());
    }

    @Transactional
    public void continuousSensorReading(Long sensorId, double value) {
        ContinuousSensor sensor = continuousSensorRepository.findById(sensorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No sensor with that id"));
        KieSession kieSession = sessionManager.getSecuritySession(false);
        ContinuousSensorEvent event = new ContinuousSensorEvent(sensor.getType(), sensor.getRoom().getId(), sensorId,
                value);

        kieSession.insert(event);
        kieSession.getAgenda().getAgendaGroup("security_template").setFocus();
        kieSession.fireAllRules();

        kieSession.getAgenda().getAgendaGroup("security_forward").setFocus();
        kieSession.fireAllRules();

        List<DiscretSensorEvent> result = kieSession
                .getObjects(new ClassObjectFilter(DiscretSensorEvent.class)).stream()
                .map(o -> (DiscretSensorEvent) o).filter(e -> e.getType().equals("calledPolice"))
                .collect(Collectors.toList());

        // if (!result.isEmpty()) {
        // sessionManager.getSecuritySession(true);
        // System.out.println("null");
        // List<Security> securities = securityRepository.findAll();
        // for (Security security1 : securities) {
        // security1.setCurrentRoomId(null);
        // securityRepository.save(security1);
        // securityRepository.flush();

        // }
        // }
    }

    @Transactional
    public void discretSensorReading(Long sensorId) {
        DiscretSensor sensor = discretSensorRepository.findById(sensorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No sensor with that id"));
        KieSession kieSession = sessionManager.getSecuritySession(false);

        DiscretSensorEvent event = new DiscretSensorEvent(sensor.getType(), sensor.getRoom().getId(), sensorId);

        kieSession.insert(event);
        kieSession.getAgenda().getAgendaGroup("security_forward").setFocus();
        kieSession.fireAllRules();

        List<DiscretSensorEvent> result = kieSession
                .getObjects(new ClassObjectFilter(DiscretSensorEvent.class)).stream()
                .map(o -> (DiscretSensorEvent) o).filter(e -> e.getType().equals("calledPolice"))
                .collect(Collectors.toList());

        // if (!result.isEmpty()) {
        // sessionManager.getSecuritySession(true);
        // System.out.println("null");
        // List<Security> securities = securityRepository.findAll();
        // for (Security security1 : securities) {
        // security1.setCurrentRoomId(null);
        // securityRepository.save(security1);
        // securityRepository.flush();

        // }
        // }
    }

    @Transactional
    public void cameraSensorReading(String type, Long sensorId) {
        Camera sensor = cameraRepository.findById(sensorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No camera with that id"));
        KieSession kieSession = sessionManager.getSecuritySession(false);

        DiscretSensorEvent event = new DiscretSensorEvent(type, sensor.getRoom().getId(), sensorId);

        kieSession.insert(event);
        kieSession.getAgenda().getAgendaGroup("security_forward").setFocus();
        kieSession.fireAllRules();

        List<DiscretSensorEvent> result = kieSession
                .getObjects(new ClassObjectFilter(DiscretSensorEvent.class)).stream()
                .map(o -> (DiscretSensorEvent) o).filter(e -> e.getType().equals("calledPolice"))
                .collect(Collectors.toList());

        // if (!result.isEmpty()) {
        // System.out.println("null");
        // sessionManager.getSecuritySession(true);
        // List<Security> securities = securityRepository.findAll();
        // for (Security security1 : securities) {
        // security1.setCurrentRoomId(null);
        // securityRepository.save(security1);
        // securityRepository.flush();

        // }
        // }
    }

    @Transactional
    public void securitySensorReading(String type, Long sensorId) {
        Security security = securityRepository.findById(sensorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No camera with that id"));
        KieSession kieSession = sessionManager.getSecuritySession(false);

        DiscretSensorEvent event = new DiscretSensorEvent(type, security.getCurrentRoomId(), sensorId);

        kieSession.insert(event);
        kieSession.getAgenda().getAgendaGroup("security_forward").setFocus();
        kieSession.fireAllRules();
        List<DiscretSensorEvent> result = kieSession
                .getObjects(new ClassObjectFilter(DiscretSensorEvent.class)).stream()
                .map(o -> (DiscretSensorEvent) o).filter(e -> e.getType().equals("calledPolice"))
                .collect(Collectors.toList());

        // if (!result.isEmpty()) {
        // System.out.println("null");
        // sessionManager.getSecuritySession(true);
        // List<Security> securities = securityRepository.findAll();
        // for (Security security1 : securities) {
        // security1.setCurrentRoomId(null);
        // securityRepository.save(security1);
        // securityRepository.flush();

        // }
        // }
    }

    public List<SensorDTO> getContinuousSensor(Long buildingId) {
        return continuousSensorRepository.findAllByRoomBuildingId(buildingId).stream()
                .map(s -> new SensorDTO(s.getId(), s.getType(), s.getRoom().getId(),
                        s.getConfig().getCriticalLowValue(), s.getConfig().getCriticalHighValue(), false, null, null))
                .collect(Collectors.toList());
    }

    public List<SensorDTO> getDiscreteSensors(Long buildingId) {
        return discretSensorRepository.findAllByRoomBuildingId(buildingId).stream()
                .map(s -> new SensorDTO(s.getId(), s.getType(), s.getRoom().getId(),
                        -1, -1, false, null, null))
                .collect(Collectors.toList());
    }

    public List<SensorDTO> getCameraSensors(Long buildingId) {
        return cameraRepository.findAllByRoomBuildingId(buildingId).stream()
                .map(s -> new SensorDTO(s.getId(), "camera", s.getRoom().getId(),
                        -1, -1, s.isOn(), null, null))
                .collect(Collectors.toList());
    }

    public List<SensorDTO> getAllSensors(Long buildingId) {
        List<SensorDTO> list1 = getContinuousSensor(buildingId);
        List<SensorDTO> list2 = getDiscreteSensors(buildingId);
        List<SensorDTO> list3 = getCameraSensors(buildingId);
        List<SensorDTO> list4 = getSecuritySensors(buildingId);
        return Stream.of(list1, list2, list3, list4)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private List<SensorDTO> getSecuritySensors(Long buildingId) {
        return securityRepository.findAllByBuildingId(buildingId).stream()
                .map(s -> new SensorDTO(s.getId(), "security", null,
                        -1, -1, false, s.getCurrentRoomId(), s.getBuildingId()))
                .collect(Collectors.toList());
    }

    // @PreDestroy
    // public void cleanup() {
    // kieSession.dispose();
    // }
}
