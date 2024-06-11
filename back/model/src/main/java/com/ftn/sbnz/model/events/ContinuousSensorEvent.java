package com.ftn.sbnz.model.events;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

import com.ftn.sbnz.model.models.Level;

@Role(Role.Type.EVENT)
@Expires("30m")
public class ContinuousSensorEvent {
    public Long deviceId;
    public Double value;
    public String type;
    public Long roomId;
    public Level level;
    public boolean processed;

    public ContinuousSensorEvent() {
    }

    public ContinuousSensorEvent(String type, Long roomId, Long deviceId, Double value, Level level) {
        this.type = type;
        this.deviceId = deviceId;
        this.value = value;
        this.roomId = roomId;
        this.level = level;
    }

    public ContinuousSensorEvent(String type, Long roomId, Long deviceId, Double value) {
        this.type = type;
        this.deviceId = deviceId;
        this.value = value;
        this.roomId = roomId;
        this.level = Level.LOW;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

}
