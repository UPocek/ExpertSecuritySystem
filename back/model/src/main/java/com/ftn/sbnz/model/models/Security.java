package com.ftn.sbnz.model.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Security {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public Long currentRoom;

    public Security() {
        this.currentRoom = null;
    }

    public void notifyOnCaution(Room room) {
    };

    public void callPolice(Room room) {
    };

    public void notifyTeam(Room room) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    };
}
