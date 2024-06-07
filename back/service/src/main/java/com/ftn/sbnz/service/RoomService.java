package com.ftn.sbnz.service;

import java.util.Optional;

import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ftn.sbnz.repository.IRoomRepository;
import com.ftn.sbnz.managers.SessionManager;
import com.ftn.sbnz.model.models.ExtraGearResponse;
import com.ftn.sbnz.model.models.Level;
import com.ftn.sbnz.model.models.Room;
import com.ftn.sbnz.model.models.RoomRequest;
import com.ftn.sbnz.model.models.WorkResponse;

@Service
public class RoomService {
    @Autowired
    private IRoomRepository roomRepository;

    @Autowired
    private SessionManager sessionManager;

    public Room addRoom(String name, Long buildingId) {
        Optional<Room> optionalBuilding = roomRepository.findByName(name);
        if (optionalBuilding.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Optional<Room> optionalRoom = roomRepository.findByName(name);
        if (optionalRoom.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Room room = new Room(name, optionalBuilding.get());
        roomRepository.save(room);
        roomRepository.flush();

        return room;
    }

    public void getRoomConfig(String type, double size, Level level, Long roomId) {
        KieSession kieSession = sessionManager.getConfigSession();
        roomRepository.findById(roomId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room with that id not found"));

        RoomRequest rr = new RoomRequest(type, size, level, roomId);
        kieSession.insert(rr);
        kieSession.fireAllRules();
        for (Object item : kieSession.getObjects()) {
            System.out.println(item);
        }
    }

    public void addWorkResponse(Long roomId, String type, boolean success) {
        KieSession kieSession = sessionManager.getConfigSession();
        roomRepository.findById(roomId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room with that id not found"));

        WorkResponse wr = new WorkResponse(roomId, type, success);
        kieSession.insert(wr);
        kieSession.fireAllRules();
        for (Object item : kieSession.getObjects()) {
            System.out.println(item);
        }
    }

    public void addExtraGearResponse(Long roomId, String type, boolean response) {
        KieSession kieSession = sessionManager.getConfigSession();
        roomRepository.findById(roomId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room with that id not found"));

        ExtraGearResponse egr = new ExtraGearResponse(roomId, type, response);
        kieSession.insert(egr);
        kieSession.fireAllRules();
        for (Object item : kieSession.getObjects()) {
            System.out.println(item);
        }
    }
}
