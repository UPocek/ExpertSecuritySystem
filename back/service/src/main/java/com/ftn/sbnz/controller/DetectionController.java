package com.ftn.sbnz.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ftn.sbnz.service.DetectionService;

@RestController
@RequestMapping("/api/detection")
public class DetectionController {

    private static Logger log = LoggerFactory.getLogger(SensorController.class);

    @Autowired
    private DetectionService detectionService;

    @PostMapping()
    public void addPeopleDetection(@RequestParam String location) {
        detectionService.detectPerson(location);

        log.debug("Added peopledetection: ");

    }

}
