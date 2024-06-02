package com.ftn.sbnz.managers;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ftn.sbnz.model.models.AggregationToStore;
import com.ftn.sbnz.model.models.Location;
import com.ftn.sbnz.model.models.Room;
import com.ftn.sbnz.repository.IAggregationsRepository;
import com.ftn.sbnz.repository.IRoomRepository;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class SessionManager {
    private static HashMap<String, KieSession> sessions = new HashMap<>();
    private static HashMap<String, KieSession> aggregationSessions = new HashMap<>();
    private static KieSession reportSessions;

    @Autowired
    private KieContainer kieContainer;

    @Autowired
    private IAggregationsRepository aggregationsRepository;

    @Autowired
    private IRoomRepository roomRepository;

    public KieSession getSession(String key) {
        if (reportSessions != null) {
            return reportSessions;
        }

        if (!sessions.containsKey(key)) {
            var session = kieContainer.newKieSession();
            sessions.put(key, session);
        }

        var session = sessions.get(key);

        return session;
    }

    public KieSession updateSession(String key, KieSession updatedSession) {

        sessions.put(key, updatedSession);

        return sessions.get(key);
    }

    public KieSession getAggregateSession(String key) {
        if (!aggregationSessions.containsKey(key)) {
            var session = kieContainer.newKieSession("cepKsession");

            aggregationSessions.put(key, session);
        }

        var session = aggregationSessions.get(key);
        session.setGlobal("aggregationRepository", aggregationsRepository);

        return session;
    }

    @Transactional
    public List<Room> getBottomLevelRooms() {
        List<Room> allRooms = roomRepository.findAll();
        return allRooms.stream()
                .filter(room -> roomRepository.findByIsContainedIn(room).isEmpty())
                .collect(Collectors.toList());
    }

    public KieSession getReportSession() {

        var reportSession = kieContainer.newKieSession("ReportSession");
        var rooms = roomRepository.findAll();
        for (Room r : rooms) {
            if (r.getIsContainedIn() == null) {
                reportSession.insert(new Location(r.getName(), r.getName()));
            } else {
                reportSession
                        .insert(new Location(r.getName(), r.getIsContainedIn().getName()));
            }
        }

        var aggregatedObjects = aggregationsRepository.findAll();
        for (AggregationToStore a : aggregatedObjects) {
            reportSession.insert(a.getAggregationDetection());
        }

        return reportSession;
    }

    public void closeSession(String key) {
        var session = sessions.get(key);
        sessions.remove(key);

        session.dispose();
    }
}
