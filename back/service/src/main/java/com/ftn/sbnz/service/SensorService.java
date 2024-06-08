package com.ftn.sbnz.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
import com.ftn.sbnz.model.models.ProductReportResult;
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

    // @PostConstruct
    // public void initialize() {
    // kieSession = sessionManager.getSession("sensorsKsession");
    // updateSessionWithSensors();
    // }

    public ContinuousSensor addContinuousSensor(String sensorType, Long roomId) {
        Optional<Room> room = roomRepository.findById(roomId);
        if (room.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No room with that id");
        }
        ContinuousSensor sensor = new ContinuousSensor(sensorType, room.get());
        continuousSensorRepository.save(sensor);
        continuousSensorRepository.flush();

        sessionManager.updateSecuritySession();

        return sensor;
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

    public DiscretSensor addDiscretSensor(String sensorType, Long roomId) {
        Optional<Room> room = roomRepository.findById(roomId);
        if (room.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No room with that id");
        }
        DiscretSensor sensor = discretSensorRepository.save(new DiscretSensor(sensorType, room.get()));
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

    public Security addSecurity(Long buildingId) {
        Security security = securityRepository.save(new Security(buildingId));
        securityRepository.flush();
        return security;
    }

    public void continuousSensorReading(Long sensorId, double value) {
        ContinuousSensor sensor = continuousSensorRepository.findById(sensorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No sensor with that id"));
        KieSession kieSession = sessionManager.getSecuritySession();
        ContinuousSensorEvent event = new ContinuousSensorEvent(sensor.getType(), sensor.getRoom().getId(), sensorId,
                value);

        kieSession.insert(event);
        kieSession.getAgenda().getAgendaGroup("security_template").setFocus();
        kieSession.fireAllRules();
        for (ContinuousSensorEvent o : kieSession.getObjects(new ClassObjectFilter(ContinuousSensorEvent.class))
                .stream()
                .map(o -> (ContinuousSensorEvent) o).collect(Collectors.toList())) {
            System.out.println(o.isProcessed());
        }

        kieSession.getAgenda().getAgendaGroup("security_forward").setFocus();
        kieSession.fireAllRules();
        // for (ContinuousSensorEvent o : kieSession.getObjects(new
        // ClassObjectFilter(ContinuousSensorEvent.class))
        // .stream()
        // .map(o -> (ContinuousSensorEvent) o).collect(Collectors.toList())) {
        // System.out.println(o.getLevel());
        // }

        // for (Room o : kieSession.getObjects(new ClassObjectFilter(Room.class))
        // .stream()
        // .map(o -> (Room) o).collect(Collectors.toList())) {
        // System.out.println(o.getAlarm());
        // }
    }

    public void discretSensorReading(Long sensorId) {
        DiscretSensor sensor = discretSensorRepository.findById(sensorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No sensor with that id"));
        KieSession kieSession = sessionManager.getSecuritySession();

        DiscretSensorEvent event = new DiscretSensorEvent(sensor.getType(), sensor.getRoom().getId(), sensorId);

        kieSession.insert(event);
        kieSession.getAgenda().getAgendaGroup("security_forward").setFocus();
        kieSession.fireAllRules();
    }

    public void cameraSensorReading(String type, Long sensorId) {
        Camera sensor = cameraRepository.findById(sensorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No camera with that id"));
        KieSession kieSession = sessionManager.getSecuritySession();

        DiscretSensorEvent event = new DiscretSensorEvent(type, sensor.getRoom().getId(), sensorId);

        kieSession.insert(event);
        kieSession.getAgenda().getAgendaGroup("security_forward").setFocus();
        kieSession.fireAllRules();
        // for (DiscretSensorEvent o : kieSession.getObjects(new
        // ClassObjectFilter(DiscretSensorEvent.class))
        // .stream()
        // .map(o -> (DiscretSensorEvent) o).collect(Collectors.toList())) {
        // System.out.println(o.getType());
        // }

        // for (Room o : kieSession.getObjects(new ClassObjectFilter(Room.class))
        // .stream()
        // .map(o -> (Room) o).collect(Collectors.toList())) {
        // System.out.println(o.getAlarm());
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
