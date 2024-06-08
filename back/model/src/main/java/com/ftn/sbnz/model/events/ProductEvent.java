package com.ftn.sbnz.model.events;

import java.util.Date;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

@Role(Role.Type.EVENT)
@Timestamp("timeStamp")
@Expires("15s")
public class ProductEvent {
    public String productGroup;
    public String act;
    public String customerId;
    public Date timeStamp;
    public double price;

    public ProductEvent() {
    }

    public ProductEvent(String productGroup, String act, String customerId, double price) {
        this.productGroup = productGroup;
        this.act = act;
        this.customerId = customerId;
        this.timeStamp = new Date();
        this.price = price;
    }

    public String getAct() {
        return act;
    }

    public void setAct(String act) {
        this.act = act;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProductGroup() {
        return productGroup;
    }

    public void setProductGroup(String productGroup) {
        this.productGroup = productGroup;
    }

}
