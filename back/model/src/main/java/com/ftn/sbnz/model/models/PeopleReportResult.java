package com.ftn.sbnz.model.models;

import java.util.Date;

public class PeopleReportResult {
    public double peopleCount;
    public Date startDate;
    public String location;
    public int interval;

    public PeopleReportResult() {
    }

    public PeopleReportResult(double peopleCount, Date startDate, String location, int interval) {
        this.peopleCount = peopleCount;
        this.startDate = startDate;
        this.location = location;
        this.interval = interval;
    }

    public double getPeopleCount() {
        return peopleCount;
    }

    public void setPeopleCount(double peopleCount) {
        this.peopleCount = peopleCount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

}
