package com.ftn.sbnz.model.models;

import javax.persistence.Embeddable;

@Embeddable
public class Configuration {
    private int criticalLowValue;
    private int criticalHighValue;

    public Configuration() {
        this.criticalLowValue = 1;
        this.criticalHighValue = 5;
    }

    public Configuration(int criticalLowValue, int criticalHighValue) {
        this.criticalLowValue = criticalLowValue;
        this.criticalHighValue = criticalHighValue;
    }

    public int getCriticalLowValue() {
        return criticalLowValue;
    }

    public void setCriticalLowValue(int criticalLowValue) {
        this.criticalLowValue = criticalLowValue;
    }

    public int getCriticalHighValue() {
        return criticalHighValue;
    }

    public void setCriticalHighValue(int criticalHighValue) {
        this.criticalHighValue = criticalHighValue;
    }

}
