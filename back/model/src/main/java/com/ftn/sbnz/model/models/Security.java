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

    public Long currentRoomId;
    public Long buildingId;

    public Security() {
    }

    public Security(Long buildingId) {
        this.buildingId = buildingId;
        this.currentRoomId = null;
    }

    public void notifyOnCaution(Long roomId) {
        this.currentRoomId = roomId;
    };

    public void callPolice(Long roomId) {
        System.out.println("Security called police");
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    };
}
