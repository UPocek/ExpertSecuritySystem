package com.ftn.sbnz.model.models;

import javax.persistence.Embedded;
import javax.persistence.Entity;

@Entity
public class ContinuousSensor extends ObserverSensor {
    private String type;
    @Embedded
    private Configuration config;

    public ContinuousSensor() {
        super();
    }

    public ContinuousSensor(String type, Room room) {
        super(room);
        this.type = type;
        this.config = new Configuration();
    }

    public ContinuousSensor(Long id, Level level, String type) {
        super(id, level);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Configuration getConfig() {
        return config;
    }

    public void setConfig(Configuration config) {
        this.config = config;
    }

}
