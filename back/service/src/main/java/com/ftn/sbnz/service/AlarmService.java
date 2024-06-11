package com.ftn.sbnz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ftn.sbnz.dtos.MessageDTO;
import com.ftn.sbnz.model.models.Alarm;
import com.ftn.sbnz.repository.IRoomRepository;
import com.ftn.sbnz.ws.SocketHandler;

@Service
public class AlarmService {
    @Autowired
    private IRoomRepository roomRepository;

    @Async
    @Scheduled(fixedRate = 10000, initialDelayString = "${timing.initialScheduledDelay}")
    protected void notifyUserOnDeviceOnOffStatus() {
        roomRepository.findAll().forEach(room -> {
            var alarm = room.getAlarm();
            if (alarm != Alarm.GREEN) {
                var message = new MessageDTO(room.getId(), alarm.name(),
                        String.format("Room %s fired %s alarm because of disaster", room.name, alarm));
                SocketHandler.sendMessage(message);
            }
        });
    }

}
