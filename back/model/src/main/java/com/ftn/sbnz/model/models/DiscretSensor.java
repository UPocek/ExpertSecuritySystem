package com.ftn.sbnz.model.models;

import javax.persistence.Entity;

@Entity
public class DiscretSensor extends ObserverSensor {

    private String type;

    public DiscretSensor(String type, int checkTime, Room room) {
        super(checkTime, room);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
