package com.ftn.sbnz.model.events;

import java.util.Date;

import org.kie.api.definition.type.Role;

@Role(Role.Type.EVENT)
public class ProductReturned {
    public Long productId;
    public String customerId;
    public Date timeStamp;
    public double price;

    public ProductReturned() {
    }

    public ProductReturned(String customerId, Long productId, Date timeStamp, double price) {
        this.productId = productId;
        this.customerId = customerId;
        this.timeStamp = timeStamp;
        this.price = price;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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

}
