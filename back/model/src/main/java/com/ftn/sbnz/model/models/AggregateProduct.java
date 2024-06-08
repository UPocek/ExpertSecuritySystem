package com.ftn.sbnz.model.models;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Embeddable;

import org.kie.api.definition.type.Role;

@Embeddable
@Role(Role.Type.EVENT)
public class AggregateProduct {
    public String id;
    public String parentId;
    public int previousInterval;
    public int interval;
    public String productGroup;
    public Date timeStamp = new Date();
    public Long quantity;
    public String act;
    public boolean isProcessed;
    public double price;

    public AggregateProduct() {
    }

    public AggregateProduct(int interval, int previousInterval, String productGroup, String act,
            Long quantity,
            double price) {
        this.id = UUID.randomUUID().toString();
        this.previousInterval = previousInterval;
        this.interval = interval;
        this.act = act;
        this.quantity = quantity;
        this.productGroup = productGroup;
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

    public int getPreviousInterval() {
        return previousInterval;
    }

    public void setPreviousInterval(int previousInterval) {
        this.previousInterval = previousInterval;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getAct() {
        return act;
    }

    public void setAct(String act) {
        this.act = act;
    }

    public boolean isProcessed() {
        return isProcessed;
    }

    public void setProcessed(boolean isProcessed) {
        this.isProcessed = isProcessed;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getProductGroup() {
        return productGroup;
    }

    public void setProductGroup(String productGroup) {
        this.productGroup = productGroup;
    }

}
