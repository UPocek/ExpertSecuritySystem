package com.ftn.sbnz.dtos;

import java.util.List;

import com.ftn.sbnz.model.models.ExtraGearRequest;
import com.ftn.sbnz.model.models.SensorRequest;
import com.ftn.sbnz.model.models.WorkRequest;

public class RoomConfigResponseDTO {
    public List<WorkRequest> workRequest;
    public List<ExtraGearRequest> extraGearRequest;
    public List<SensorRequest> sensors;

    public RoomConfigResponseDTO() {
    }

    public List<WorkRequest> getWorkRequest() {
        return workRequest;
    }

    public void setWorkRequest(List<WorkRequest> workRequest) {
        this.workRequest = workRequest;
    }

    public List<ExtraGearRequest> getExtraGearRequest() {
        return extraGearRequest;
    }

    public void setExtraGearRequest(List<ExtraGearRequest> extraGearRequest) {
        this.extraGearRequest = extraGearRequest;
    }

    public List<SensorRequest> getSensors() {
        return sensors;
    }

    public void setSensors(List<SensorRequest> sensors) {
        this.sensors = sensors;
    }

}
