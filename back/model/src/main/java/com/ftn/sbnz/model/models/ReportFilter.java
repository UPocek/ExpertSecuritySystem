package com.ftn.sbnz.model.models;

import java.util.Date;

public class ReportFilter {
    public Date startDate;
    public Date endDate;
    public String location;
    public String productGroup;
    public int interval;
    public String type;

    public ReportFilter() {
    }

    public ReportFilter(String type) {
        this.type = type;
    }

    public ReportFilter(Date startDate, Date endDate, String location, String productGroup, String type) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.productGroup = productGroup;
        this.type = type;
    }

    public ReportFilter(Date startDate, Date endDate, String location, String productGroup, int interval, String type) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.productGroup = productGroup;
        this.interval = interval;
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProductGroup() {
        return productGroup;
    }

    public void setProductGroup(String productGroup) {
        this.productGroup = productGroup;
    }

}
