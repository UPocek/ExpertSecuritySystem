package com.ftn.sbnz.model.models;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AggregationToStore {
    @Id
    @Column(name = "id_base")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_base;

    @Embedded
    private AggregatedDetection aggregationDetection;

    public AggregationToStore() {
    }

    public AggregationToStore(AggregatedDetection aggregationDetection) {
        this.aggregationDetection = aggregationDetection;
    }

    public AggregatedDetection getAggregationDetection() {
        return aggregationDetection;
    }

    public void setAggregationDetection(AggregatedDetection aggregationDetection) {
        this.aggregationDetection = aggregationDetection;
    }

    public Long getId_base() {
        return id_base;
    }

    public void setId_base(Long id_base) {
        this.id_base = id_base;
    }

}
