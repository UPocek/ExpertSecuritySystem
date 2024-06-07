package com.ftn.sbnz.model.models;

public class RequestProductAggregate {
    public int interval;
    public Long productId;
    public String act;

    public RequestProductAggregate() {
    }

    public RequestProductAggregate(int interval, Long productId, String act) {
        this.interval = interval;
        this.productId = productId;
        this.act = act;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getAct() {
        return act;
    }

    public void setAct(String act) {
        this.act = act;
    }

}
