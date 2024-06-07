package com.ftn.sbnz.model.models;

import javax.persistence.Entity;

@Entity
public class Camera extends ReactorSensor {

    public boolean isOn;

    public Camera() {

    }

    public Camera(Room room) {
        super(room);
        this.isOn = false;
    }

    @Override
    public void react() {
        super.react();
    }

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean isOn) {
        this.isOn = isOn;
    }

}
