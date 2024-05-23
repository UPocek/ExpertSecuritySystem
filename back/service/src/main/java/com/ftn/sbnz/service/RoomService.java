package com.ftn.sbnz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ftn.sbnz.repository.IRoomRepository;
import com.ftn.sbnz.model.models.Room;

@Service
public class RoomService {

    @Autowired
    private IRoomRepository roomRepository;

    public Room addRoom() {
        Room room = new Room();
        roomRepository.save(room);
        roomRepository.flush();

        return room;
    }
}
