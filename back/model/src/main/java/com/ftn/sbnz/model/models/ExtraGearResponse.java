
package com.ftn.sbnz.model.models;

public class ExtraGearResponse {
    public Long roomId;
    public String type;
    public boolean response;

    public ExtraGearResponse() {
    }

    public ExtraGearResponse(Long roomId, String type, boolean response) {
        this.roomId = roomId;
        this.type = type;
        this.response = response;
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

    public boolean isResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }

}
