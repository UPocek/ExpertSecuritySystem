package com.ftn.sbnz.dtos;

public class RoomDTO {
    public Long id;
    public Long isContainedInId;
    public String name;
    public String isContainedIn;

    public RoomDTO() {
    }

    public RoomDTO(String name, String isContainedIn) {
        this.name = name;
        this.isContainedIn = isContainedIn;
    }

    public RoomDTO(Long id, Long isContainedInId, String name, String isContainedIn) {
        this.id = id;
        this.isContainedInId = isContainedInId;
        this.name = name;
        this.isContainedIn = isContainedIn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsContainedIn() {
        return isContainedIn;
    }

    public void setIsContainedIn(String isContainedIn) {
        this.isContainedIn = isContainedIn;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIsContainedInId() {
        return isContainedInId;
    }

    public void setIsContainedInId(Long isContainedInId) {
        this.isContainedInId = isContainedInId;
    }

}
