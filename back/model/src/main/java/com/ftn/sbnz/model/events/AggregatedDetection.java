package com.ftn.sbnz.model.events;

import org.kie.api.definition.type.Role;

@Role(Role.Type.EVENT)
public class AggregatedDetection {
    public String location;
    public int numberOfPeople;
    public String interval;
    public boolean done;

    public AggregatedDetection() {
    }

    public AggregatedDetection(String location, int numberOfPeople, String interval) {
        this.location = location;
        this.numberOfPeople = numberOfPeople;
        this.interval = interval;
        this.done = false;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
