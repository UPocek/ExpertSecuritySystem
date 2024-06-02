package com.ftn.sbnz.model.models;

public class AggregatePeople {
    public int interval;
    public String location;

    public AggregatePeople() {
    }

    public AggregatePeople(int interval, String location) {
        this.interval = interval;
        this.location = location;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
