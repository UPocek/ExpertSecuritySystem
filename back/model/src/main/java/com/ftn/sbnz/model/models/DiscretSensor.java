package com.ftn.sbnz.model.models;

import javax.persistence.Entity;

@Entity
public class DiscretSensor extends ObserverSensor {

    private String type;

    public DiscretSensor() {
    }

    public DiscretSensor(String type, Room room) {
        super(room);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
