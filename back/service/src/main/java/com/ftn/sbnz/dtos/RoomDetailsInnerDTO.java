package com.ftn.sbnz.dtos;

public class RoomDetailsInnerDTO {

    private Long roomId;
    private int size;
    private String securityLevel;
    private String type;

    public RoomDetailsInnerDTO() {
    }

    public RoomDetailsInnerDTO(Long roomId, int size, String securityLevel, String type) {
        this.roomId = roomId;
        this.size = size;
        this.securityLevel = securityLevel;
        this.type = type;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getSecurityLevel() {
        return securityLevel;
    }

    public void setSecurityLevel(String securityLevel) {
        this.securityLevel = securityLevel;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
