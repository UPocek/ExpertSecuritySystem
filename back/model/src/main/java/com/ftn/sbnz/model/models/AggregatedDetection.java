package com.ftn.sbnz.model.models;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Embeddable;

import org.kie.api.definition.type.Position;
import org.kie.api.definition.type.Role;

@Embeddable
@Role(Role.Type.EVENT)
public class AggregatedDetection implements Serializable {
    @Position(0)
    public String id;
    @Position(1)
    public String parentId;
    public int interval;
    public int previousInterval;
    public String location;
    public Aggregation aggregation;
    public boolean isProcessed;
    public Date timeStamp = new Date();

    public AggregatedDetection() {
    }

    public AggregatedDetection(AggregatedDetection agg) {
        this.id = agg.id;
        this.parentId = agg.parentId;
        this.interval = agg.interval;
        this.previousInterval = agg.previousInterval;
        this.location = agg.location;
        this.aggregation = agg.aggregation;
        this.isProcessed = agg.isProcessed;
        this.timeStamp = agg.timeStamp;
    }

    public AggregatedDetection(int interval, int previousInterval, String location,
            Aggregation aggregation) {
        this.id = UUID.randomUUID().toString();
        this.interval = interval;
        this.previousInterval = previousInterval;
        this.location = location;
        this.aggregation = aggregation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public int getPreviousInterval() {
        return previousInterval;
    }

    public void setPreviousInterval(int previousInterval) {
        this.previousInterval = previousInterval;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Aggregation getAggregation() {
        return aggregation;
    }

    public void setAggregation(Aggregation aggregation) {
        this.aggregation = aggregation;
    }

    public boolean isProcessed() {
        return isProcessed;
    }

    public void setProcessed(boolean isProcessed) {
        this.isProcessed = isProcessed;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

}
