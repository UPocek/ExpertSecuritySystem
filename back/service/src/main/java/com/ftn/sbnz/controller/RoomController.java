package com.ftn.sbnz.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.sbnz.dtos.RoomConfigRequestsDTO;
import com.ftn.sbnz.dtos.RoomConfigResponseDTO;
import com.ftn.sbnz.dtos.RoomDTO;
import com.ftn.sbnz.dtos.RoomDetailsDTO;
import com.ftn.sbnz.model.models.Room;
import com.ftn.sbnz.service.RoomService;

@RestController
@RequestMapping("/api/room")
public class RoomController {

    private static Logger log = LoggerFactory.getLogger(SensorController.class);

    @Autowired
    private RoomService roomService;

    @GetMapping()
    public Room getRoom(@RequestParam Long id) {
        Room room = roomService.getRoom(id);
        return room;
    }

    @GetMapping("/building")
    public List<RoomDTO> getBuilding() {
        return roomService.getBuilding();
    }

    @GetMapping("/all")
    public List<Room> getRooms() {
        return roomService.getRooms();
    }

    @GetMapping("/all_building")
    public List<Room> getRoomsForBuilding(@RequestParam Long id) {
        return roomService.getRoomsForBuilding(id);
    }

    @GetMapping("/leaf")
    public List<Room> getLeafRooms() {
        return roomService.getBottomLevelRooms();
    }

    @PostMapping()
    public Room addRoom(@RequestParam String name, @RequestParam Long containedInRoomId,
            @RequestParam Long buildingId) {
        Room newRoom = roomService.addRoom(name, containedInRoomId, buildingId);

        log.debug("Added new room: " + newRoom);

        return newRoom;
    }

    @PostMapping("/building")
    public List<RoomDTO> addBuilding(@RequestBody List<RoomDTO> roomDTOs) {
        return roomService.addBuilding(roomDTOs);
    }

    @PostMapping("/config")
    public List<RoomConfigResponseDTO> getRoomConfig(@RequestBody RoomDetailsDTO roomDetailsDTO) {
        return roomService.getRoomConfig(roomDetailsDTO);

    }

    @PostMapping("/config/requests")
    public List<RoomConfigResponseDTO> addResponses(@RequestBody RoomConfigRequestsDTO responses) {
        return roomService.addResponses(responses);
    }
}
