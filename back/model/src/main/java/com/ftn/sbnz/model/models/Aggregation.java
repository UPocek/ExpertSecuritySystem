package com.ftn.sbnz.model.models;

import javax.persistence.Embeddable;

@Embeddable
public class Aggregation {
    public Double min;
    public Double max;
    public Double sum;
    public Double average;
    public Long count;

    public Aggregation() {
    }

    public Aggregation(Double min, Double max, Double sum, Double average, Long count) {
        this.min = min;
        this.max = max;
        this.sum = sum;
        this.average = average;
        this.count = count;
    }

    public Aggregation(int min, int max, int sum, Double average, Long count) {
        this.min = (double) min;
        this.max = (double) max;
        this.sum = (double) sum;
        this.average = average;
        this.count = count;
    }

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public Double getAverage() {
        return average;
    }

    public void setAverage(Double average) {
        this.average = average;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

}
