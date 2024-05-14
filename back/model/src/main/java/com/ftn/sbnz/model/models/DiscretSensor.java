package com.ftn.sbnz.model.models;

import javax.persistence.Entity;

@Entity
public class DiscretSensor extends ObserverSensor {

    private String type;

    public DiscretSensor(int checkTime) {
        super(checkTime);
        // TODO Auto-generated constructor stub
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
