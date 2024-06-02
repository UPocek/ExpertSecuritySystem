package com.ftn.sbnz.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ftn.sbnz.model.models.PeopleReportResult;
import com.ftn.sbnz.service.DetectionService;

@RestController
@RequestMapping("/api/detection")
public class DetectionController {

    private static Logger log = LoggerFactory.getLogger(SensorController.class);

    @Autowired
    private DetectionService detectionService;

    @PostMapping()
    public void addPeopleDetection(@RequestParam String location) {
        detectionService.test();
        log.debug("Added peopledetection");

    }

    @GetMapping("/total_daily")
    public List<PeopleReportResult> totalDailyReport(@RequestParam String location,
            @RequestParam String startDate,
            @RequestParam String endDate) {

        return detectionService.totalDailyReport(location, startDate, endDate);
    }

    @GetMapping("/total_weekly")
    public List<PeopleReportResult> totalWeeklyReport(@RequestParam String location,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        return detectionService.totalWeeklyReport(location, startDate, endDate);

    }

    @GetMapping("/total_monthly")
    public List<PeopleReportResult> totalMonthlyReport(@RequestParam String location,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        return detectionService.totalMonthlyReport(location, startDate, endDate);

    }

    @GetMapping("/part_of_day_trend")
    public List<PeopleReportResult> partOfDayTrend(@RequestParam String location,
            @RequestParam String startDate,
            @RequestParam String endDate, @RequestParam String partOfDay) {
        return detectionService.totalPartOfDayTrends(location, startDate, endDate, partOfDay);

    }

    @GetMapping("/ranking")
    public List<PeopleReportResult> ranking() {
        return detectionService.rankingReport();

    }

    @GetMapping("/average_person_in_store")
    public List<PeopleReportResult> averagePersonInStoreMonth(@RequestParam Long storeId,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        return detectionService.averagePersonInStoreMonth(storeId, startDate, endDate);

    }

    @GetMapping("/average_people_reccuring")
    public List<PeopleReportResult> averagePartOfDayOfWeekTrends(@RequestParam String location,
            @RequestParam String startDate,
            @RequestParam String endDate, @RequestParam String partOfDay, @RequestParam int dayOfWeek) {
        return detectionService.averagePartOfDayOfWeekTrends(location, startDate, endDate, partOfDay, dayOfWeek);

    }

    @GetMapping("/max_people_reccuring")
    public List<PeopleReportResult> maxPartOfDayOfWeekTrends(@RequestParam String location,
            @RequestParam String startDate,
            @RequestParam String endDate, @RequestParam String partOfDay, @RequestParam int dayOfWeek) {
        return detectionService.maxPartOfDayOfWeekTrends(location, startDate, endDate, partOfDay, dayOfWeek);

    }

    @GetMapping("/min_people_reccuring")
    public List<PeopleReportResult> minPartOfDayOfWeekTrends(@RequestParam String location,
            @RequestParam String startDate,
            @RequestParam String endDate, @RequestParam String partOfDay, @RequestParam int dayOfWeek) {
        return detectionService.minPartOfDayOfWeekTrends(location, startDate, endDate, partOfDay, dayOfWeek);

    }

}
