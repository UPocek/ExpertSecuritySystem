package com.ftn.sbnz.dtos;

import java.util.List;

public class RoomConfigRequestsDTO {
    private List<RoomResponseDTO> config;

    public RoomConfigRequestsDTO() {
    }

    public List<RoomResponseDTO> getConfig() {
        return config;
    }

    public void setConfig(List<RoomResponseDTO> config) {
        this.config = config;
    }

}
