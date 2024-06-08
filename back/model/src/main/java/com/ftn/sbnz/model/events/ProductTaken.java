package com.ftn.sbnz.model.events;

import java.util.Date;

import org.kie.api.definition.type.Role;

@Role(Role.Type.EVENT)
public class ProductTaken {
    public String productGroup;
    public String customerId;
    public Date timeStamp;
    public double price;

    public ProductTaken() {
    }

    public ProductTaken(String customerId, String productGroup, Date timeStamp, double price) {
        this.productGroup = productGroup;
        this.customerId = customerId;
        this.timeStamp = timeStamp;
        this.price = price;
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
