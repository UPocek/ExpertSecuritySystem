package com.ftn.sbnz.model.models;

public class WorkRequest {
    public Long roomId;
    public String type;
    public boolean success;
    public boolean isProcessed;

    public WorkRequest() {
    }

    public WorkRequest(Long roomId, String type) {
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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isProcessed() {
        return isProcessed;
    }

    public void setProcessed(boolean isProcessed) {
        this.isProcessed = isProcessed;
    }

}
