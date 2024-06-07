package com.ftn.sbnz.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.sbnz.model.models.Level;
import com.ftn.sbnz.model.models.Room;
import com.ftn.sbnz.service.RoomService;

@RestController
@RequestMapping("/api/room")
public class RoomController {

    private static Logger log = LoggerFactory.getLogger(SensorController.class);

    @Autowired
    private RoomService roomService;

    @PostMapping()
    public Room addRoom(@RequestParam String name, @RequestParam Long buildingId) {
        Room newRoom = roomService.addRoom(name, buildingId);

        log.debug("Added new room: " + newRoom);

        return newRoom;
    }

    @GetMapping("/config")
    public void getRoomConfig(@RequestParam Long roomId, @RequestParam String type, @RequestParam double size,
            @RequestParam String level) {
        roomService.getRoomConfig(type, size, Level.valueOf(level), roomId);

    }

    @PostMapping("/config/work")
    public void addWorkResponse(Long roomId, String type, boolean response) {
        roomService.addWorkResponse(roomId, type, response);
    }

    @PostMapping("/config/gear")
    public void addGearResponse(Long roomId, String type, boolean response) {
        roomService.addExtraGearResponse(roomId, type, response);
    }
}
