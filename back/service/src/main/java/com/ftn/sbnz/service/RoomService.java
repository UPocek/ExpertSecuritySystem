package com.ftn.sbnz.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ftn.sbnz.repository.IRoomRepository;
import com.ftn.sbnz.model.models.Room;

@Service
public class RoomService {
    @Autowired
    private IRoomRepository roomRepository;

    public Room addRoom(String name) {
        Optional<Room> optionalRoom = roomRepository.findByName(name);
        if (optionalRoom.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Room room = new Room(name);
        roomRepository.save(room);
        roomRepository.flush();

        return room;
    }
}
