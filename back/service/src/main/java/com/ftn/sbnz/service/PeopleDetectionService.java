package com.ftn.sbnz.service;

import org.drools.core.ClassObjectFilter;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ftn.sbnz.managers.SessionManager;
import com.ftn.sbnz.model.events.PersonDetectionEvent;
import com.ftn.sbnz.model.models.ReportFilter;
import com.ftn.sbnz.model.models.Room;
import com.ftn.sbnz.repository.IRoomRepository;
import com.ftn.sbnz.model.models.PeopleReportResult;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PeopleDetectionService {

    @Autowired
    private SessionManager sessionManager;

    @Autowired
    private IRoomRepository roomRepository;

    public List<PeopleReportResult> totalDailyReport(String location, String startDate, String endDate) {
        KieSession kieSession = sessionManager.getPeopleReportSession();

        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        Date startDateParsed;
        Date endDateParsed;
        try {
            startDateParsed = formatter.parse(startDate);
            endDateParsed = formatter.parse(endDate);

            List<Date[]> dailyRanges = getDailyRanges(startDateParsed, endDateParsed);

            for (Date[] d : dailyRanges) {
                ReportFilter filter = new ReportFilter(d[0], d[1], location, null, 1440, "daily");
                kieSession.insert(filter);
            }
            kieSession.getAgenda().getAgendaGroup("peopleReport").setFocus();
            kieSession.fireAllRules();

            List<PeopleReportResult> result = kieSession
                    .getObjects(new ClassObjectFilter(PeopleReportResult.class)).stream()
                    .map(o -> (PeopleReportResult) o).collect(Collectors.toList());

            result.sort(Comparator.comparing(PeopleReportResult::getStartDate));

            kieSession.getAgenda().getAgendaGroup("cleanup").setFocus();
            kieSession.fireAllRules();
            return result;
        } catch (ParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public static List<Date[]> getDailyRanges(Date startDate, Date endDate) {
        List<Date[]> dailyRanges = new ArrayList<>();

        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);
        startCal.set(Calendar.MINUTE, 0);
        startCal.set(Calendar.SECOND, 0);
        startCal.set(Calendar.MILLISECOND, 0);

        startCal.setFirstDayOfWeek(Calendar.MONDAY);

        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);
        endCal.set(Calendar.MINUTE, 0);
        endCal.set(Calendar.SECOND, 0);
        endCal.set(Calendar.MILLISECOND, 0);

        while (startCal.before(endCal) || startCal.equals(endCal)) {
            Date dayStart = startCal.getTime();

            // Move calendar to end of the day
            Calendar dayEndCal = (Calendar) startCal.clone();
            dayEndCal.set(Calendar.HOUR_OF_DAY, 23);
            dayEndCal.set(Calendar.MINUTE, 59);
            dayEndCal.set(Calendar.SECOND, 59);
            dayEndCal.set(Calendar.MILLISECOND, 999);
            Date dayEnd = dayEndCal.getTime();

            // Add the daily range to the list
            dailyRanges.add(new Date[] { dayStart, dayEnd });

            // Move calendar to the start of the next day
            startCal.add(Calendar.DAY_OF_MONTH, 1);
            startCal.set(Calendar.HOUR_OF_DAY, 0);
            startCal.set(Calendar.MINUTE, 0);
            startCal.set(Calendar.SECOND, 0);
            startCal.set(Calendar.MILLISECOND, 0);
        }

        return dailyRanges;
    }

    public List<PeopleReportResult> totalWeeklyReport(String location, String startDate, String endDate) {
        KieSession kieSession = sessionManager.getPeopleReportSession();

        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        Date startDateParsed;
        Date endDateParsed;
        try {
            startDateParsed = formatter.parse(startDate);
            endDateParsed = formatter.parse(endDate);

            List<Date[]> weeklyRanges = getWeeklyRanges(startDateParsed, endDateParsed);

            for (Date[] d : weeklyRanges) {
                ReportFilter filter = new ReportFilter(d[0], d[1], location, null, 1440, "weekly");
                kieSession.insert(filter);
            }
            kieSession.getAgenda().getAgendaGroup("peopleReport").setFocus();
            kieSession.fireAllRules();
            List<PeopleReportResult> result = kieSession
                    .getObjects(new ClassObjectFilter(PeopleReportResult.class)).stream()
                    .map(o -> (PeopleReportResult) o).collect(Collectors.toList());

            result.sort(Comparator.comparing(PeopleReportResult::getPeopleCount));
            kieSession.getAgenda().getAgendaGroup("cleanup").setFocus();
            kieSession.fireAllRules();
            return result;
        } catch (ParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

    }

    public static List<Date[]> getWeeklyRanges(Date startDate, Date endDate) {
        List<Date[]> weeklyRanges = new ArrayList<>();

        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);
        startCal.set(Calendar.HOUR_OF_DAY, 0);
        startCal.set(Calendar.MINUTE, 0);
        startCal.set(Calendar.SECOND, 0);
        startCal.set(Calendar.MILLISECOND, 0);

        // Adjust startCal to the start of the week
        startCal.setFirstDayOfWeek(Calendar.MONDAY);
        startCal.set(Calendar.DAY_OF_WEEK, startCal.getFirstDayOfWeek());

        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);
        endCal.set(Calendar.HOUR_OF_DAY, 23);
        endCal.set(Calendar.MINUTE, 59);
        endCal.set(Calendar.SECOND, 59);
        endCal.set(Calendar.MILLISECOND, 999);

        while (startCal.before(endCal)) {
            Date weekStart = startCal.getTime();

            // Move calendar to end of the week
            startCal.add(Calendar.DAY_OF_WEEK, 6);
            Date weekEnd = startCal.getTime();

            // Add the week range to the list
            weeklyRanges.add(new Date[] { weekStart, weekEnd });

            // Move calendar to the start of the next week
            startCal.add(Calendar.DAY_OF_WEEK, 1);
        }

        return weeklyRanges;
    }

    public List<PeopleReportResult> totalMonthlyReport(String location, String startDate, String endDate) {
        KieSession kieSession = sessionManager.getPeopleReportSession();

        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        Date startDateParsed;
        Date endDateParsed;
        try {
            startDateParsed = formatter.parse(startDate);
            endDateParsed = formatter.parse(endDate);

            List<Date[]> monthlyRanges = getMonthlyRanges(startDateParsed, endDateParsed);

            for (Date[] d : monthlyRanges) {
                System.out.println(d[0] + " " + d[1]);
                ReportFilter filter = new ReportFilter(d[0], d[1], location, null, 1440, "monthly");
                kieSession.insert(filter);
            }
            kieSession.getAgenda().getAgendaGroup("peopleReport").setFocus();
            kieSession.fireAllRules();
            List<PeopleReportResult> result = kieSession
                    .getObjects(new ClassObjectFilter(PeopleReportResult.class)).stream()
                    .map(o -> (PeopleReportResult) o).collect(Collectors.toList());

            result.sort(Comparator.comparing(PeopleReportResult::getStartDate));
            kieSession.getAgenda().getAgendaGroup("cleanup").setFocus();
            kieSession.fireAllRules();
            return result;
        } catch (ParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

    }

    public static List<Date[]> getMonthlyRanges(Date startDate, Date endDate) {
        List<Date[]> monthlyRanges = new ArrayList<>();

        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);
        startCal.set(Calendar.HOUR_OF_DAY, 0);
        startCal.set(Calendar.MINUTE, 0);
        startCal.set(Calendar.SECOND, 0);
        startCal.set(Calendar.MILLISECOND, 0);

        // Adjust startCal to the start of the month
        startCal.setFirstDayOfWeek(Calendar.MONDAY);
        startCal.set(Calendar.DAY_OF_MONTH, 1);

        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);
        endCal.set(Calendar.HOUR_OF_DAY, 23);
        endCal.set(Calendar.MINUTE, 59);
        endCal.set(Calendar.SECOND, 59);
        endCal.set(Calendar.MILLISECOND, 999);

        while (startCal.before(endCal)) {
            Date monthStart = startCal.getTime();

            // Move calendar to the end of the month
            startCal.set(Calendar.DAY_OF_MONTH, startCal.getActualMaximum(Calendar.DAY_OF_MONTH));
            Date monthEnd = startCal.getTime();

            // Add the month range to the list
            monthlyRanges.add(new Date[] { monthStart, monthEnd });

            // Move calendar to the start of the next month
            startCal.add(Calendar.DAY_OF_MONTH, 1);
            startCal.set(Calendar.DAY_OF_MONTH, 1);
        }

        return monthlyRanges;
    }

    public List<PeopleReportResult> totalPartOfDayTrends(String location, String startDate, String endDate,
            String partOfDay) {
        KieSession kieSession = sessionManager.getPeopleReportSession();

        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        Date startDateParsed;
        Date endDateParsed;
        try {
            startDateParsed = formatter.parse(startDate);
            endDateParsed = formatter.parse(endDate);

            List<Date[]> monthlyRanges = getDailyTimeRanges(startDateParsed, endDateParsed, partOfDay);

            for (Date[] d : monthlyRanges) {
                ReportFilter filter = new ReportFilter(d[0], d[1], location, null, 15, "daytime");
                kieSession.insert(filter);
            }
            kieSession.getAgenda().getAgendaGroup("peopleReport").setFocus();
            kieSession.fireAllRules();
            List<PeopleReportResult> result = kieSession
                    .getObjects(new ClassObjectFilter(PeopleReportResult.class)).stream()
                    .map(o -> (PeopleReportResult) o).collect(Collectors.toList());

            result.sort(Comparator.comparing(PeopleReportResult::getStartDate));
            kieSession.getAgenda().getAgendaGroup("cleanup").setFocus();
            kieSession.fireAllRules();
            return result;
        } catch (ParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

    }

    public static List<Date[]> getDailyTimeRanges(Date startDate, Date endDate, String period) {
        List<Date[]> dailyTimeRanges = new ArrayList<>();

        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);
        startCal.set(Calendar.MINUTE, 0);
        startCal.set(Calendar.SECOND, 0);
        startCal.set(Calendar.MILLISECOND, 0);

        startCal.setFirstDayOfWeek(Calendar.MONDAY);

        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);
        endCal.set(Calendar.MINUTE, 0);
        endCal.set(Calendar.SECOND, 0);
        endCal.set(Calendar.MILLISECOND, 0);

        while (!startCal.after(endCal)) {
            Date periodStart = null;
            Date periodEnd = null;

            switch (period.toLowerCase()) {
                case "morning":
                    startCal.set(Calendar.HOUR_OF_DAY, 6);
                    periodStart = startCal.getTime();
                    startCal.set(Calendar.HOUR_OF_DAY, 12);
                    periodEnd = startCal.getTime();
                    break;
                case "midday":
                    startCal.set(Calendar.HOUR_OF_DAY, 12);
                    periodStart = startCal.getTime();
                    startCal.set(Calendar.HOUR_OF_DAY, 18);
                    periodEnd = startCal.getTime();
                    break;
                case "afternoon":
                    startCal.set(Calendar.HOUR_OF_DAY, 18);
                    periodStart = startCal.getTime();
                    startCal.set(Calendar.HOUR_OF_DAY, 24);
                    periodEnd = startCal.getTime();
                    break;
                default:
                    throw new IllegalArgumentException(
                            "Invalid period specified. Use 'morning', 'midday', or 'afternoon'.");
            }

            // Add the time range to the list
            dailyTimeRanges.add(new Date[] { periodStart, periodEnd });

            // Move to the next day and reset the hours to the start of the period
            startCal.add(Calendar.DAY_OF_MONTH, 1);
        }

        return dailyTimeRanges;
    }

    public List<PeopleReportResult> rankingReport() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -10);
        Date starDate = calendar.getTime();

        // Get the date for tomorrow
        calendar.add(Calendar.YEAR, 10);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date endDate = calendar.getTime();

        KieSession kieSession = sessionManager.getPeopleReportSession();
        ReportFilter filter = new ReportFilter(starDate, endDate, null, null, 1440, "ranking");
        kieSession.insert(filter);
        kieSession.getAgenda().getAgendaGroup("peopleReport").setFocus();
        kieSession.fireAllRules();
        List<PeopleReportResult> result = kieSession
                .getObjects(new ClassObjectFilter(PeopleReportResult.class)).stream()
                .map(o -> (PeopleReportResult) o).collect(Collectors.toList());
        result.sort(Comparator.comparingDouble(PeopleReportResult::getPeopleCount).reversed());
        kieSession.getAgenda().getAgendaGroup("cleanup").setFocus();
        kieSession.fireAllRules();
        return result;
    }

    public List<PeopleReportResult> averagePersonInStoreMonth(Long storeId, String startDate,
            String endDate) {
        Optional<Room> roomOptional = roomRepository.findById(storeId);
        if (roomOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        KieSession kieSession = sessionManager.getPeopleReportSession();

        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        Date startDateParsed;
        Date endDateParsed;
        try {
            startDateParsed = formatter.parse(startDate);
            endDateParsed = formatter.parse(endDate);

            List<Date[]> monthlyRanges = getMonthlyRanges(startDateParsed, endDateParsed);

            for (Date[] d : monthlyRanges) {
                ReportFilter filter = new ReportFilter(d[0], d[1], roomOptional.get().getName(), null, 1440,
                        "average_people_store");
                kieSession.insert(filter);
            }
            kieSession.getAgenda().getAgendaGroup("peopleReport").setFocus();
            kieSession.fireAllRules();
            List<PeopleReportResult> result = kieSession
                    .getObjects(new ClassObjectFilter(PeopleReportResult.class)).stream()
                    .map(o -> (PeopleReportResult) o).collect(Collectors.toList());

            result.sort(Comparator.comparing(PeopleReportResult::getStartDate));
            kieSession.getAgenda().getAgendaGroup("cleanup").setFocus();
            kieSession.fireAllRules();
            return result;
        } catch (ParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

    }

    public List<PeopleReportResult> averagePartOfDayOfWeekTrends(String location, String startDate,
            String endDate,
            String partOfDay, int day) {
        KieSession kieSession = sessionManager.getPeopleReportSession();

        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        Date startDateParsed;
        Date endDateParsed;
        try {
            startDateParsed = formatter.parse(startDate);
            endDateParsed = formatter.parse(endDate);

            List<Date[]> dailyRanges = getDailyTimeRangesForTheWeekDay(startDateParsed, endDateParsed, partOfDay,
                    day);

            for (Date[] d : dailyRanges) {
                ReportFilter filter = new ReportFilter(d[0], d[1], location, null, 15, "average_people_reccuring");
                kieSession.insert(filter);
            }
            kieSession.getAgenda().getAgendaGroup("peopleReport").setFocus();
            kieSession.fireAllRules();
            List<PeopleReportResult> result = kieSession
                    .getObjects(new ClassObjectFilter(PeopleReportResult.class)).stream()
                    .map(o -> (PeopleReportResult) o).collect(Collectors.toList());

            result.sort(Comparator.comparing(PeopleReportResult::getStartDate));
            kieSession.getAgenda().getAgendaGroup("cleanup").setFocus();
            kieSession.fireAllRules();
            return result;
        } catch (ParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

    }

    public List<PeopleReportResult> maxPartOfDayOfWeekTrends(String location, String startDate,
            String endDate,
            String partOfDay, int day) {
        KieSession kieSession = sessionManager.getPeopleReportSession();

        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        Date startDateParsed;
        Date endDateParsed;
        try {
            startDateParsed = formatter.parse(startDate);
            endDateParsed = formatter.parse(endDate);

            List<Date[]> dailyRanges = getDailyTimeRangesForTheWeekDay(startDateParsed, endDateParsed, partOfDay,
                    day);

            for (Date[] d : dailyRanges) {
                ReportFilter filter = new ReportFilter(d[0], d[1], location, null, 15, "max_people_reccuring");
                kieSession.insert(filter);
            }
            kieSession.getAgenda().getAgendaGroup("peopleReport").setFocus();
            kieSession.fireAllRules();
            List<PeopleReportResult> result = kieSession
                    .getObjects(new ClassObjectFilter(PeopleReportResult.class)).stream()
                    .map(o -> (PeopleReportResult) o).collect(Collectors.toList());

            result.sort(Comparator.comparing(PeopleReportResult::getStartDate));
            kieSession.getAgenda().getAgendaGroup("cleanup").setFocus();
            kieSession.fireAllRules();
            return result;
        } catch (ParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

    }

    public List<PeopleReportResult> minPartOfDayOfWeekTrends(String location, String startDate,
            String endDate,
            String partOfDay, int day) {
        KieSession kieSession = sessionManager.getPeopleReportSession();

        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        Date startDateParsed;
        Date endDateParsed;
        try {
            startDateParsed = formatter.parse(startDate);
            endDateParsed = formatter.parse(endDate);

            List<Date[]> dailyRanges = getDailyTimeRangesForTheWeekDay(startDateParsed, endDateParsed, partOfDay,
                    day);

            for (Date[] d : dailyRanges) {
                ReportFilter filter = new ReportFilter(d[0], d[1], location, null, 15, "min_people_reccuring");
                kieSession.insert(filter);
            }
            kieSession.getAgenda().getAgendaGroup("peopleReport").setFocus();
            kieSession.fireAllRules();
            List<PeopleReportResult> result = kieSession
                    .getObjects(new ClassObjectFilter(PeopleReportResult.class)).stream()
                    .map(o -> (PeopleReportResult) o).collect(Collectors.toList());

            result.sort(Comparator.comparing(PeopleReportResult::getStartDate));
            kieSession.getAgenda().getAgendaGroup("cleanup").setFocus();
            kieSession.fireAllRules();
            return result;
        } catch (ParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

    }

    public static List<Date[]> getDailyTimeRangesForTheWeekDay(Date startDate, Date endDate, String period,
            int dayOfWeek) {
        List<Date[]> dailyTimeRanges = new ArrayList<>();

        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);
        startCal.set(Calendar.MINUTE, 0);
        startCal.set(Calendar.SECOND, 0);
        startCal.set(Calendar.MILLISECOND, 0);

        startCal.setFirstDayOfWeek(Calendar.MONDAY);

        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);
        endCal.set(Calendar.MINUTE, 0);
        endCal.set(Calendar.SECOND, 0);
        endCal.set(Calendar.MILLISECOND, 0);

        while (!startCal.after(endCal)) {
            if (startCal.get(Calendar.DAY_OF_WEEK) == dayOfWeek) {
                Date periodStart = null;
                Date periodEnd = null;

                switch (period.toLowerCase()) {
                    case "morning":
                        startCal.set(Calendar.HOUR_OF_DAY, 6);
                        periodStart = startCal.getTime();
                        startCal.set(Calendar.HOUR_OF_DAY, 12);
                        periodEnd = startCal.getTime();
                        break;
                    case "midday":
                        startCal.set(Calendar.HOUR_OF_DAY, 12);
                        periodStart = startCal.getTime();
                        startCal.set(Calendar.HOUR_OF_DAY, 18);
                        periodEnd = startCal.getTime();
                        break;
                    case "afternoon":
                        startCal.set(Calendar.HOUR_OF_DAY, 18);
                        periodStart = startCal.getTime();
                        startCal.set(Calendar.HOUR_OF_DAY, 24);
                        periodEnd = startCal.getTime();
                        break;
                    default:
                        throw new IllegalArgumentException(
                                "Invalid period specified. Use 'morning', 'midday', or 'afternoon'.");
                }

                // Add the time range to the list
                dailyTimeRanges.add(new Date[] { periodStart, periodEnd });
            }

            // Move to the next day and reset the hours to the start of the period
            startCal.add(Calendar.DAY_OF_MONTH, 1);
        }

        return dailyTimeRanges;
    }

    public void insertDetection(String location, int numberOfPeople) {
        KieSession kieSession = sessionManager.getAggregatePeopleSession();

        Room room = roomRepository.findByName(location).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No room with that location name"));

        PersonDetectionEvent pde = new PersonDetectionEvent(room.getName(), numberOfPeople);
        kieSession.insert(pde);
        kieSession.fireAllRules();

        System.out.println("All facts in session:");

        for (Object item : kieSession.getObjects()) {
            System.out.println(item);
        }
    }
}
