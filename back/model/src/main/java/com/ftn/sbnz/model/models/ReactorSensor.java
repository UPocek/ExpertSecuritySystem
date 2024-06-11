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
public class ReactorSensor {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    public Long id;
    @ManyToOne
    public Room room;

    public ReactorSensor() {
    }

    public ReactorSensor(Room room) {
        this.room = room;
    }

    public void react() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

}
