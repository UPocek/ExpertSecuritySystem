package com.ftn.sbnz.service;

import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.sbnz.model.events.PersonDetectionEvent;

@Service
public class DetectionService {

    @Autowired
    private KieSession kieSession;

    public void detectPerson(String location) {
        PersonDetectionEvent event = new PersonDetectionEvent(location, 5);
        PersonDetectionEvent event1 = new PersonDetectionEvent(location, 5);
        PersonDetectionEvent event2 = new PersonDetectionEvent(location, 5);
        kieSession.insert(event);
        kieSession.insert(event1);
        kieSession.insert(event2);
        int n = kieSession.fireAllRules();
        System.out.println("Number of rules fired: " + n);
        System.out.println("All facts in session:");
        for (Object fact : kieSession.getObjects()) {
            System.out.println(fact);
        }

    }
}
