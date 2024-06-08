package com.ftn.sbnz.dtos;

import java.util.List;

public class RoomDetailsDTO {
    private List<RoomDetailsInnerDTO> config;

    public RoomDetailsDTO() {
    }

    public List<RoomDetailsInnerDTO> getConfig() {
        return config;
    }

    public void setConfig(List<RoomDetailsInnerDTO> config) {
        this.config = config;
    }

}
