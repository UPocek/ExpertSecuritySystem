package com.ftn.sbnz.model.models;

public class ExtraGearRequest {
    public Long roomId;
    public String type;

    public ExtraGearRequest() {
    }

    public ExtraGearRequest(Long roomId, String type) {
        this.roomId = roomId;
        this.type = type;
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

}
