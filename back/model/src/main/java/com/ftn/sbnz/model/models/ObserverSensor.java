package com.ftn.sbnz.model.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class ObserverSensor {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    public Long id;
    public int checkTime;
    public boolean onOff;
    public Level level;
    @ManyToOne
    public Room room;

    public ObserverSensor() {
    }

    public ObserverSensor(int checkTime, Room room) {
        this.room = room;
        this.checkTime = checkTime;
        this.onOff = false;
        this.level = Level.MEDIUM;
    }

    public ObserverSensor(Long id, Level level) {
        this.id = id;
        this.level = level;
    }

    public int getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(int checkTime) {
        this.checkTime = checkTime;
    }

    public boolean isOn() {
        return onOff;
    }

    public void setOn(boolean onOff) {
        this.onOff = onOff;
    }

    public Long getId() {
        return id;
    }

    public boolean isOnOff() {
        return onOff;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOnOff(boolean onOff) {
        this.onOff = onOff;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }
}