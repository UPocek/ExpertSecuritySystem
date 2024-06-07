package com.ftn.sbnz.model.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String name;
    public Alarm alarm;
    public boolean locked;
    @ManyToOne
    public Room isContainedIn;
    @ManyToOne
    public Room building;

    public Room() {
    }

    public Room(String name, Room building) {
        this.alarm = Alarm.GREEN;
        this.locked = false;
        this.name = name;
        this.building = building;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Alarm getAlarm() {
        return alarm;
    }

    public void setAlarm(Alarm alarm) {
        this.alarm = alarm;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public Room getIsContainedIn() {
        return isContainedIn;
    }

    public void setIsContainedIn(Room isContainedIn) {
        this.isContainedIn = isContainedIn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Room getBuilding() {
        return building;
    }

    public void setBuilding(Room building) {
        this.building = building;
    }

}
