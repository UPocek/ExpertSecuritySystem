package com.ftn.sbnz.dtos;

import java.util.List;

import com.ftn.sbnz.model.models.ProductReportResult;

public class ReturnSellRateDTO {
    public List<ProductReportResult> returned;
    public List<ProductReportResult> taken;

    public ReturnSellRateDTO() {
    }

    public ReturnSellRateDTO(List<ProductReportResult> returned, List<ProductReportResult> taken) {
        this.returned = returned;
        this.taken = taken;
    }

    public List<ProductReportResult> getReturned() {
        return returned;
    }

    public void setReturned(List<ProductReportResult> returned) {
        this.returned = returned;
    }

    public List<ProductReportResult> getTaken() {
        return taken;
    }

    public void setTaken(List<ProductReportResult> taken) {
        this.taken = taken;
    }

}
