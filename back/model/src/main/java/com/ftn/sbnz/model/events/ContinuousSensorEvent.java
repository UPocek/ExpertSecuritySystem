package com.ftn.sbnz.model.events;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

@Role(Role.Type.EVENT)
@Expires("30m")
public class ContinuousSensorEvent {
    public Long deviceId;
    public Double value;
    public Long timestamp;
    public String type;
    public boolean processed;
    public Long roomId;

    public ContinuousSensorEvent() {
    }

    public ContinuousSensorEvent(String type, Long roomId, Long deviceId, Double value, Long timestamp) {
        this.type = type;
        this.deviceId = deviceId;
        this.value = value;
        this.timestamp = timestamp;
        this.processed = false;
        this.roomId = roomId;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

}
