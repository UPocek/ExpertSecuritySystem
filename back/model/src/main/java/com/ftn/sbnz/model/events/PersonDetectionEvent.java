package com.ftn.sbnz.model.events;

import org.kie.api.definition.type.Role;

@Role(Role.Type.EVENT)
public class PersonDetectionEvent {
    public String location;
    public int numberOfPeople;
    public boolean done;

    public PersonDetectionEvent() {
    }

    public PersonDetectionEvent(String location, int numberOfPeople) {
        this.location = location;
        this.numberOfPeople = numberOfPeople;
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

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

}
