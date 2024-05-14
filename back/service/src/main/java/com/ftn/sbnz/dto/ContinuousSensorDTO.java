package com.ftn.sbnz.dto;

public class ContinuousSensorDTO {
    private String type;
    private int checkTime;

    public ContinuousSensorDTO() {
    }

    public ContinuousSensorDTO(String type, int checkTime) {
        this.checkTime = checkTime;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(int checkTime) {
        this.checkTime = checkTime;
    }

}
