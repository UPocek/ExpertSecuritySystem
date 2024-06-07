package com.ftn.sbnz.model.models;

public class SensorRequest {
    public Long roomId;
    public String type;
    public String building;
    public Level level;
    public int quantity;
    public String power;

    public SensorRequest() {
    }

    public SensorRequest(Long roomId, String type, String building, Level level, int quantity) {
        this.roomId = roomId;
        this.type = type;
        this.building = building;
        this.level = level;
        this.quantity = quantity;
        this.power = "basic";
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

}
