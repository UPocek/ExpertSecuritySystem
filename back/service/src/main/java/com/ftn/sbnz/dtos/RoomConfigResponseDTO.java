package com.ftn.sbnz.dtos;

import com.ftn.sbnz.model.models.ExtraGearRequest;
import com.ftn.sbnz.model.models.WorkRequest;

public class RoomConfigResponseDTO {
    public WorkRequest workRequest;
    public ExtraGearRequest extraGearRequest;

    public RoomConfigResponseDTO() {
    }

    public RoomConfigResponseDTO(WorkRequest workRequest, ExtraGearRequest extraGearRequest) {
        this.workRequest = workRequest;
        this.extraGearRequest = extraGearRequest;
    }

    public WorkRequest getWorkRequest() {
        return workRequest;
    }

    public void setWorkRequest(WorkRequest workRequest) {
        this.workRequest = workRequest;
    }

    public ExtraGearRequest getExtraGearRequest() {
        return extraGearRequest;
    }

    public void setExtraGearRequest(ExtraGearRequest extraGearRequest) {
        this.extraGearRequest = extraGearRequest;
    }

}
