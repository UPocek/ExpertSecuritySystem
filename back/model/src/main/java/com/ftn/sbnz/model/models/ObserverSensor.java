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
    public Level level;
    @ManyToOne
    public Room room;

    public ObserverSensor() {
    }

    public ObserverSensor(Room room) {
        this.room = room;
        this.level = Level.MEDIUM;
    }

    public ObserverSensor(Long id, Level level) {
        this.id = id;
        this.level = level;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

}