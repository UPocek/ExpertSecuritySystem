package com.ftn.sbnz.model.events;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

@Role(Role.Type.EVENT)
@Expires("30m")
public class DiscretSensorEvent {
    public Long roomId;
    public Long deviceId;
    public String type;
    public boolean detected;

    public DiscretSensorEvent() {
    }

    public DiscretSensorEvent(String type, Long roomId, Long deviceId) {
        this.roomId = roomId;
        this.deviceId = deviceId;
        this.type = type;
        this.detected = true;
    }

    public DiscretSensorEvent(Long roomId, String type) {
        this.roomId = roomId;
        this.type = type;
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

}
