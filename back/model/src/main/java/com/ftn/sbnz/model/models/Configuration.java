package com.ftn.sbnz.model.models;

import javax.persistence.Embeddable;

@Embeddable
public class Configuration {
    private int criticalLowValue;
    private int criticalMediumValue;
    private int criticalHighValue;

    public Configuration() {
        this.criticalLowValue = 0;
        this.criticalMediumValue = 0;
        this.criticalHighValue = 0;
    }

    public Configuration(int criticalLowValue, int criticalMediumValue, int criticalHighValue) {
        this.criticalLowValue = criticalLowValue;
        this.criticalMediumValue = criticalMediumValue;
        this.criticalHighValue = criticalHighValue;
    }

    public int getCriticalLowValue() {
        return criticalLowValue;
    }

    public void setCriticalLowValue(int criticalLowValue) {
        this.criticalLowValue = criticalLowValue;
    }

    public int getCriticalMediumValue() {
        return criticalMediumValue;
    }

    public void setCriticalMediumValue(int criticalMediumValue) {
        this.criticalMediumValue = criticalMediumValue;
    }

    public int getCriticalHighValue() {
        return criticalHighValue;
    }

    public void setCriticalHighValue(int criticalHighValue) {
        this.criticalHighValue = criticalHighValue;
    }

}
