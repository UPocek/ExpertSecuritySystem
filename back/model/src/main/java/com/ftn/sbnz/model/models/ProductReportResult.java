package com.ftn.sbnz.model.models;

import java.util.Date;

public class ProductReportResult {
    public double value;
    public Date startDate;
    public String productGroup;
    public int interval;

    public ProductReportResult() {
    }

    public ProductReportResult(double value, Date startDate, String productGroup, int interval) {
        this.value = value;
        this.startDate = startDate;
        this.productGroup = productGroup;
        this.interval = interval;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getProductGroup() {
        return productGroup;
    }

    public void setProductGroup(String productGroup) {
        this.productGroup = productGroup;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

}
