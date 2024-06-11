package com.ftn.sbnz.model.models;

public class ExtraGearRequest {
    public Long roomId;
    public String type;
    public boolean response;
    public boolean isProcessed;

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

    public boolean isProcessed() {
        return isProcessed;
    }

    public void setProcessed(boolean isProcessed) {
        this.isProcessed = isProcessed;
    }

    public boolean isResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }

}
