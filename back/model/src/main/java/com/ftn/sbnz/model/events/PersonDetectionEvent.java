package com.ftn.sbnz.model.events;

import org.kie.api.definition.type.Role;

@Role(Role.Type.EVENT)
public class PersonDetectionEvent {
    public String location;
    public int numberOfPeople;

    public PersonDetectionEvent() {
    }

    public PersonDetectionEvent(String location, int numberOfPeople) {
        this.location = location;
        this.numberOfPeople = numberOfPeople;
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

}
