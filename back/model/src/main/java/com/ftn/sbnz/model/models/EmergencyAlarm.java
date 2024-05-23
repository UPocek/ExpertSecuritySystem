package com.ftn.sbnz.model.models;

import javax.persistence.Entity;

@Entity
public class EmergencyAlarm extends ReactorSensor {
    public boolean isOn;

    public EmergencyAlarm() {
    }

    @Override
    public void react() {
        // TODO Auto-generated method stub
        super.react();
    }

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean isOn) {
        this.isOn = isOn;
    }

}
