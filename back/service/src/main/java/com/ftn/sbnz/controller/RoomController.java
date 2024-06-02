package com.ftn.sbnz.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.sbnz.model.models.Room;
import com.ftn.sbnz.service.RoomService;

@RestController
@RequestMapping("/api/room")
public class RoomController {

    private static Logger log = LoggerFactory.getLogger(SensorController.class);

    @Autowired
    private RoomService roomService;

    @PostMapping()
    public Room addRoom(@RequestParam String name) {
        Room newRoom = roomService.addRoom(name);

        log.debug("Added new room: " + newRoom);

        return newRoom;
    }
}
