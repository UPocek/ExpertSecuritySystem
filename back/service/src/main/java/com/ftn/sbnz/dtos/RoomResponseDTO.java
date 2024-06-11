package com.ftn.sbnz.dtos;

import com.ftn.sbnz.model.models.ExtraGearResponse;
import com.ftn.sbnz.model.models.WorkResponse;

public class RoomResponseDTO {
    public Long roomId;
    public WorkResponse workResponse;
    public ExtraGearResponse extraGearResponse;

    public RoomResponseDTO() {
    }

    public RoomResponseDTO(WorkResponse workResponse, ExtraGearResponse extraGearResponse, Long roomId) {
        this.workResponse = workResponse;
        this.extraGearResponse = extraGearResponse;
        this.roomId = roomId;
    }

    public WorkResponse getWorkResponse() {
        return workResponse;
    }

    public void setWorkResponse(WorkResponse workResponse) {
        this.workResponse = workResponse;
    }

    public ExtraGearResponse getExtraGearResponse() {
        return extraGearResponse;
    }

    public void setExtraGearResponse(ExtraGearResponse extraGearResponse) {
        this.extraGearResponse = extraGearResponse;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

}
