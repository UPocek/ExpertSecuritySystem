package com.ftn.sbnz.model.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public Alarm alarm;
    public boolean locked;

    public Room() {
        this.alarm = Alarm.GREEN;
        this.locked = false;
    }

}
