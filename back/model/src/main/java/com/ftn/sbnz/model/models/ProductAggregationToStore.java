package com.ftn.sbnz.model.models;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ProductAggregationToStore {
    @Id
    @Column(name = "id_base")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_base;
    @Embedded
    private AggregateProduct aggregateProduct;

    public ProductAggregationToStore() {
    }

    public ProductAggregationToStore(AggregateProduct aggregateProduct) {
        this.aggregateProduct = aggregateProduct;
    }

    public Long getId_base() {
        return id_base;
    }

    public void setId_base(Long id_base) {
        this.id_base = id_base;
    }

    public AggregateProduct getAggregateProduct() {
        return aggregateProduct;
    }

    public void setAggregateProduct(AggregateProduct aggregateProduct) {
        this.aggregateProduct = aggregateProduct;
    }

}
