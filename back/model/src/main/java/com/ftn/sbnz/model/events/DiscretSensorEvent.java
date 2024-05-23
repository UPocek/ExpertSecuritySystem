package com.ftn.sbnz.model.events;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

@Role(Role.Type.EVENT)
@Expires("30m")
public class DiscretSensorEvent {
    public Long roomId;
    public Long deviceId;
    public Long timestamp;
    public String type;
    public boolean processed;
    public boolean detected;

    public DiscretSensorEvent() {
    }

    public DiscretSensorEvent(String type, Long roomId, Long deviceId) {
        this.roomId = roomId;
        this.deviceId = deviceId;
        this.type = type;
        this.processed = false;
        this.detected = true;
    }

    public DiscretSensorEvent(Long roomId, Long timestamp, String type, boolean processed) {
        this.roomId = roomId;
        this.timestamp = timestamp;
        this.type = type;
        this.processed = processed;
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

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

}
