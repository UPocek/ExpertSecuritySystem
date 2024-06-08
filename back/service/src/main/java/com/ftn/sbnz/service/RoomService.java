package com.ftn.sbnz.service;

import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.drools.core.ClassObjectFilter;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ftn.sbnz.repository.IRoomRepository;
import com.ftn.sbnz.dtos.RoomConfigResponseDTO;
import com.ftn.sbnz.dtos.RoomDTO;
import com.ftn.sbnz.managers.SessionManager;
import com.ftn.sbnz.model.models.ExtraGearRequest;
import com.ftn.sbnz.model.models.ExtraGearResponse;
import com.ftn.sbnz.model.models.Level;
import com.ftn.sbnz.model.models.Room;
import com.ftn.sbnz.model.models.RoomRequest;
import com.ftn.sbnz.model.models.WorkRequest;
import com.ftn.sbnz.model.models.WorkResponse;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomService {
    @Autowired
    private IRoomRepository roomRepository;

    @Autowired
    private SessionManager sessionManager;

    public Room addRoom(String name, Long containedInRoomId, Long buildingId) {
        Room optionalBuilding = roomRepository.findById(buildingId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Room optionalRoom = roomRepository.findById(containedInRoomId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Room room = new Room(name, optionalRoom, optionalBuilding);
        roomRepository.save(room);
        roomRepository.flush();

        return room;
    }

    public RoomConfigResponseDTO getRoomConfig(String type, double size, Level level, Long roomId) {
        KieSession kieSession = sessionManager.getConfigSession();
        roomRepository.findById(roomId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room with that id not found"));

        RoomRequest rr = new RoomRequest(type, size, level, roomId);
        kieSession.insert(rr);
        kieSession.fireAllRules();

        for (Object item : kieSession.getObjects()) {
            System.out.println(item);
        }

        List<WorkRequest> result1 = kieSession
                .getObjects(new ClassObjectFilter(WorkRequest.class)).stream()
                .map(o -> (WorkRequest) o).collect(Collectors.toList());

        RoomConfigResponseDTO dto = new RoomConfigResponseDTO();
        if (result1.isEmpty()) {
            dto.setWorkRequest(null);
        } else {
            dto.setWorkRequest(result1.get(0));
        }

        List<ExtraGearRequest> result2 = kieSession
                .getObjects(new ClassObjectFilter(ExtraGearRequest.class)).stream()
                .map(o -> (ExtraGearRequest) o).collect(Collectors.toList());

        if (result2.isEmpty()) {
            dto.setExtraGearRequest(null);
        } else {
            dto.setExtraGearRequest(result2.get(0));
        }

        return dto;
    }

    public RoomConfigResponseDTO addWorkResponse(Long roomId, String type, boolean success) {
        KieSession kieSession = sessionManager.getConfigSession();
        roomRepository.findById(roomId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room with that id not found"));

        WorkResponse wr = new WorkResponse(roomId, type, success);
        kieSession.insert(wr);
        kieSession.fireAllRules();
        for (Object item : kieSession.getObjects()) {
            System.out.println(item);
        }

        List<WorkRequest> result1 = kieSession
                .getObjects(new ClassObjectFilter(WorkRequest.class)).stream()
                .map(o -> (WorkRequest) o).collect(Collectors.toList());

        RoomConfigResponseDTO dto = new RoomConfigResponseDTO();
        if (result1.isEmpty()) {
            dto.setWorkRequest(null);
        } else {
            dto.setWorkRequest(result1.get(0));
        }

        List<ExtraGearRequest> result2 = kieSession
                .getObjects(new ClassObjectFilter(ExtraGearRequest.class)).stream()
                .map(o -> (ExtraGearRequest) o).collect(Collectors.toList());

        if (result2.isEmpty()) {
            dto.setExtraGearRequest(null);
        } else {
            dto.setExtraGearRequest(result2.get(0));
        }

        return dto;
    }

    public RoomConfigResponseDTO addExtraGearResponse(Long roomId, String type, boolean response) {
        KieSession kieSession = sessionManager.getConfigSession();
        roomRepository.findById(roomId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room with that id not found"));

        ExtraGearResponse egr = new ExtraGearResponse(roomId, type, response);
        kieSession.insert(egr);
        kieSession.fireAllRules();
        for (Object item : kieSession.getObjects()) {
            System.out.println(item);
        }

        List<WorkRequest> result1 = kieSession
                .getObjects(new ClassObjectFilter(WorkRequest.class)).stream()
                .map(o -> (WorkRequest) o).collect(Collectors.toList());

        RoomConfigResponseDTO dto = new RoomConfigResponseDTO();
        if (result1.isEmpty()) {
            dto.setWorkRequest(null);
        } else {
            dto.setWorkRequest(result1.get(0));
        }

        List<ExtraGearRequest> result2 = kieSession
                .getObjects(new ClassObjectFilter(ExtraGearRequest.class)).stream()
                .map(o -> (ExtraGearRequest) o).collect(Collectors.toList());

        if (result2.isEmpty()) {
            dto.setExtraGearRequest(null);
        } else {
            dto.setExtraGearRequest(result2.get(0));
        }

        return dto;
    }

    public Room getRoom(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room with that id not found"));
        return room;
    }

    public List<Room> getRooms() {
        return roomRepository.findAll();
    }

    @Transactional
    public List<Room> getBottomLevelRooms() {
        List<Room> allRooms = roomRepository.findAll();
        return allRooms.stream()
                .filter(room -> roomRepository.findByIsContainedIn(room).isEmpty())
                .collect(Collectors.toList());
    }

    @Transactional
    public List<RoomDTO> getBuilding() {
        Room building = roomRepository.findAll().stream().filter(e -> e.getBuilding() == null)
                .collect(Collectors.toList()).get(0);
        List<Room> rooms = roomRepository.findAllByBuildingId(building.getId());
        rooms.add(building);
        return rooms.stream().map(
                r -> new RoomDTO(r.getId(), r.getIsContainedIn() == null ? null : r.getIsContainedIn().getId(),
                        r.getName(),
                        r.getIsContainedIn() == null ? null : r.getIsContainedIn().getName()))
                .collect(Collectors.toList());

    }

    @Transactional
    public List<RoomDTO> addBuilding(List<RoomDTO> rooms) {
        RoomDTO buildingDTO = rooms.stream().filter(r -> r.isContainedIn == null).collect(Collectors.toList()).get(0);
        Room building = new Room(buildingDTO.getName());
        roomRepository.save(building);
        roomRepository.flush();

        List<Room> newCreatedRooms = new ArrayList<>();

        rooms.forEach(r -> {
            if (r.isContainedIn != null) {
                Room room = new Room(r.getName());
                roomRepository.save(room);
                roomRepository.flush();
                newCreatedRooms.add(room);
            }
        });

        newCreatedRooms.add(building);

        rooms.forEach(r -> {
            if (r.isContainedIn != null) {
                Room room = newCreatedRooms.stream().filter(r1 -> r1.getName().equals(r.getName()))
                        .collect(Collectors.toList()).get(0);
                Room containedIn = newCreatedRooms.stream().filter(r1 -> r1.getName().equals(r.isContainedIn))
                        .collect(Collectors.toList()).get(0);
                room.setIsContainedIn(containedIn);
                room.setBuilding(building);
                roomRepository.save(room);
                roomRepository.flush();
            }
        });

        return newCreatedRooms.stream().map(
                r -> r.getIsContainedIn() == null ? new RoomDTO(r.getId(), null, r.getName(), null)
                        : new RoomDTO(r.getId(), r.getIsContainedIn().getId(), r.getName(),
                                r.getIsContainedIn().getName()))
                .collect(Collectors.toList());
    }

    public List<Room> getRoomsForBuilding(Long id) {
        return roomRepository.findAllByBuildingId(id);
    }
}
