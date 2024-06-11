package com.ftn.sbnz.model.models;

public class RequestProductAggregate {
    public int interval;
    public String productGroup;
    public String act;

    public RequestProductAggregate() {
    }

    public RequestProductAggregate(int interval, String productGroup, String act) {
        this.interval = interval;
        this.productGroup = productGroup;
        this.act = act;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public String getAct() {
        return act;
    }

    public void setAct(String act) {
        this.act = act;
    }

    public String getProductGroup() {
        return productGroup;
    }

    public void setProductGroup(String productGroup) {
        this.productGroup = productGroup;
    }

}
