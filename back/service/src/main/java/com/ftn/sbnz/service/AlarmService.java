package com.ftn.sbnz.service;

import java.util.List;
import java.util.stream.Collectors;

import org.drools.core.ClassObjectFilter;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ftn.sbnz.dtos.MessageDTO;
import com.ftn.sbnz.managers.SessionManager;
import com.ftn.sbnz.model.models.Alarm;
import com.ftn.sbnz.model.models.ProductReportResult;
import com.ftn.sbnz.model.models.Room;
import com.ftn.sbnz.repository.IRoomRepository;
import com.ftn.sbnz.ws.SocketHandler;
import com.google.gson.Gson;

@Service
public class AlarmService {
    @Autowired
    private IRoomRepository roomRepository;

    @Autowired
    private SessionManager sessionManager;

    @Async
    @Scheduled(fixedRate = 10000, initialDelayString = "${timing.initialScheduledDelay}")
    protected void notifyUserOnDeviceOnOffStatus() {
        KieSession kieSession = sessionManager.getSecuritySession();
        for (Room o : kieSession.getObjects(new ClassObjectFilter(Room.class))
                .stream()
                .map(o -> (Room) o).collect(Collectors.toList())) {
            var alarm = o.getAlarm();
            if (alarm != Alarm.GREEN) {
                var message = new MessageDTO(o.getId(), alarm.name(),
                        String.format("Room %s fired %s alarm because of disaster", o.name, alarm));

                String alarmMessage = new Gson().toJson(message);
                SocketHandler.sendMessage(alarmMessage);
            }
            // roomRepository.findAll().forEach(room -> {
            // var alarm = room.getAlarm();
            // if (alarm != Alarm.GREEN) {
            // var message = new MessageDTO(room.getId(), alarm.name(),
            // String.format("Room %s fired %s alarm because of disaster", room.name,
            // alarm));

            // String alarmMessage = new Gson().toJson(message);
            // SocketHandler.sendMessage(alarmMessage);
            // }
            // });
        }
    }

}
