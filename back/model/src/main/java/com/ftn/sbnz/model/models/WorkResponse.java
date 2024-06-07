package com.ftn.sbnz.model.models;

public class WorkResponse {
    public Long roomId;
    public String type;
    public boolean success;

    public WorkResponse() {
    }

    public WorkResponse(Long roomId, String type, boolean success) {
        this.roomId = roomId;
        this.type = type;
        this.success = success;
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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

}
