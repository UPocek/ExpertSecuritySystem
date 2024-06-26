package com.ftn.sbnz.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.drools.core.ClassObjectFilter;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ftn.sbnz.dtos.ReturnSellRateDTO;
import com.ftn.sbnz.managers.SessionManager;
import com.ftn.sbnz.model.events.ProductEvent;
import com.ftn.sbnz.model.models.ProductReportResult;
import com.ftn.sbnz.model.models.ReportFilter;
import com.ftn.sbnz.repository.IProductRepository;

@Service
public class ProductDetectionService {

    @Autowired
    private SessionManager sessionManager;

    @Autowired
    private IProductRepository productRepository;

    public List<ProductReportResult> mostReturned() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -10);
        Date starDate = calendar.getTime();

        // Get the date for tomorrow
        calendar.add(Calendar.YEAR, 10);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date endDate = calendar.getTime();

        KieSession kieSession = sessionManager.getProductReportSession();
        ReportFilter filter = new ReportFilter(starDate, endDate, null, null, 1440, "most_return");
        kieSession.insert(filter);
        kieSession.getAgenda().getAgendaGroup("productReportReturn").setFocus();
        kieSession.fireAllRules();
        List<ProductReportResult> result = kieSession
                .getObjects(new ClassObjectFilter(ProductReportResult.class)).stream()
                .map(o -> (ProductReportResult) o).collect(Collectors.toList());
        result.sort(Comparator.comparingDouble(ProductReportResult::getValue).reversed());
        kieSession.getAgenda().getAgendaGroup("cleanup").setFocus();
        kieSession.fireAllRules();
        return result;

    }

    public List<ProductReportResult> sellingTrend(String product, String startDate, String endDate) {
        KieSession kieSession = sessionManager.getProductReportSession();

        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        Date startDateParsed;
        Date endDateParsed;
        try {
            startDateParsed = formatter.parse(startDate);
            endDateParsed = formatter.parse(endDate);

            List<Date[]> dailyRanges = getDailyRanges(startDateParsed, endDateParsed);

            for (Date[] d : dailyRanges) {
                ReportFilter filter = new ReportFilter(d[0], d[1], null, product, 1440, "selling_trend");
                kieSession.insert(filter);
            }
            kieSession.getAgenda().getAgendaGroup("productReportTake").setFocus();
            kieSession.fireAllRules();

            List<ProductReportResult> result = kieSession
                    .getObjects(new ClassObjectFilter(ProductReportResult.class)).stream()
                    .map(o -> (ProductReportResult) o).collect(Collectors.toList());

            result.sort(Comparator.comparing(ProductReportResult::getStartDate));

            kieSession.getAgenda().getAgendaGroup("cleanup").setFocus();
            kieSession.fireAllRules();
            return result;
        } catch (ParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public ReturnSellRateDTO takeReturnRate(String product, String startDate, String endDate) {
        KieSession kieSession = sessionManager.getProductReportSession();

        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        Date startDateParsed;
        Date endDateParsed;
        try {
            startDateParsed = formatter.parse(startDate);
            endDateParsed = formatter.parse(endDate);

            List<Date[]> dailyRanges = getDailyRanges(startDateParsed, endDateParsed);

            for (Date[] d : dailyRanges) {
                ReportFilter filter = new ReportFilter(d[0], d[1], null, product, 1440, "take_return_trend");
                kieSession.insert(filter);
            }
            kieSession.getAgenda().getAgendaGroup("productReportTake").setFocus();
            kieSession.fireAllRules();

            List<ProductReportResult> resultTake = kieSession
                    .getObjects(new ClassObjectFilter(ProductReportResult.class)).stream()
                    .map(o -> (ProductReportResult) o).collect(Collectors.toList());

            resultTake.sort(Comparator.comparing(ProductReportResult::getStartDate));

            kieSession.getAgenda().getAgendaGroup("cleanup").setFocus();
            kieSession.fireAllRules();

            for (Date[] d : dailyRanges) {
                ReportFilter filter = new ReportFilter(d[0], d[1], null, product, 1440, "take_return_trend");
                kieSession.insert(filter);
            }

            kieSession.getAgenda().getAgendaGroup("productReportReturn").setFocus();
            kieSession.fireAllRules();

            List<ProductReportResult> resultReturn = kieSession
                    .getObjects(new ClassObjectFilter(ProductReportResult.class)).stream()
                    .map(o -> (ProductReportResult) o).collect(Collectors.toList());

            resultReturn.sort(Comparator.comparing(ProductReportResult::getStartDate));

            kieSession.getAgenda().getAgendaGroup("cleanup").setFocus();
            kieSession.fireAllRules();
            return new ReturnSellRateDTO(resultReturn, resultTake);
        } catch (ParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public List<ProductReportResult> moneyReturnLoss(String product, String startDate, String endDate) {
        KieSession kieSession = sessionManager.getProductReportSession();

        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        Date startDateParsed;
        Date endDateParsed;
        try {
            startDateParsed = formatter.parse(startDate);
            endDateParsed = formatter.parse(endDate);

            List<Date[]> dailyRanges = getDailyRanges(startDateParsed, endDateParsed);

            for (Date[] d : dailyRanges) {
                ReportFilter filter = new ReportFilter(d[0], d[1], null, product, 1440, "return_loss");
                kieSession.insert(filter);
            }
            kieSession.getAgenda().getAgendaGroup("productReportReturn").setFocus();
            kieSession.fireAllRules();

            List<ProductReportResult> result = kieSession
                    .getObjects(new ClassObjectFilter(ProductReportResult.class)).stream()
                    .map(o -> (ProductReportResult) o).collect(Collectors.toList());

            result.sort(Comparator.comparing(ProductReportResult::getStartDate));

            kieSession.getAgenda().getAgendaGroup("cleanup").setFocus();
            kieSession.fireAllRules();
            return result;
        } catch (ParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public List<ProductReportResult> mostProfitableProduct() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -10);
        Date starDate = calendar.getTime();

        // Get the date for tomorrow
        calendar.add(Calendar.YEAR, 10);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date endDate = calendar.getTime();

        KieSession kieSession = sessionManager.getProductReportSession();
        ReportFilter filter = new ReportFilter(starDate, endDate, null, null, 1440, "most_sell");
        kieSession.insert(filter);
        kieSession.getAgenda().clear();
        kieSession.getAgenda().getAgendaGroup("productReportTake").setFocus();
        kieSession.fireAllRules();
        List<ProductReportResult> result = kieSession
                .getObjects(new ClassObjectFilter(ProductReportResult.class)).stream()
                .map(o -> (ProductReportResult) o).collect(Collectors.toList());
        result.sort(Comparator.comparingDouble(ProductReportResult::getValue).reversed());
        kieSession.getAgenda().getAgendaGroup("cleanup").setFocus();
        kieSession.fireAllRules();
        return result;
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

    public void insertDetection(String productGroup, String act, String customerId, double price) {
        KieSession kieSession = sessionManager.getAggregateProducteSession();

        productRepository.findByName(productGroup).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No product with that group name"));

        ProductEvent pe = new ProductEvent(productGroup, act, customerId, price);
        kieSession.insert(pe);
        kieSession.fireAllRules();

        System.out.println("All facts in session:");

        for (Object item : kieSession.getObjects()) {
            System.out.println(item);
        }
    }
}
