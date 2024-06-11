package com.ftn.sbnz.dtos;

public class SensorDTO {
    public Long id;
    public String type;
    public Long roomId;
    public int low;
    public int high;
    public boolean isOn;
    public Long currentRoomId;
    public Long buildingId;

    public SensorDTO() {
    }

    public SensorDTO(Long id, String type, Long roomId, int low, int high, boolean isOn, Long currentRoomId,
            Long buildingId) {
        this.id = id;
        this.type = type;
        this.roomId = roomId;
        this.low = low;
        this.high = high;
        this.isOn = isOn;
        this.currentRoomId = currentRoomId;
        this.buildingId = buildingId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public int getLow() {
        return low;
    }

    public void setLow(int low) {
        this.low = low;
    }

    public int getHigh() {
        return high;
    }

    public void setHigh(int high) {
        this.high = high;
    }

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean isOn) {
        this.isOn = isOn;
    }

}
