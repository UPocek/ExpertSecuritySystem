package com.ftn.sbnz.model.models;

public class RoomRequest {
    public String type;
    public double size;
    public Level level;
    public Long roomId;

    public RoomRequest() {
    }

    public RoomRequest(String type, double size, Level level, Long roomId) {
        this.type = type;
        this.size = size;
        this.level = level;
        this.roomId = roomId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

}
