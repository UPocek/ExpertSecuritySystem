package com.ftn.sbnz.model.models;

import java.util.Date;

import javax.persistence.Column;
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
    public String id;
    public String parentId;
    public int previousInterval;
    public int interval;
    public String productGroup;
    public Date timeStamp;
    public Long quantity;
    public String act;
    public boolean isProcessed;
    public double price;

    public ProductAggregationToStore() {
    }

    public ProductAggregationToStore(AggregateProduct aggregateProduct) {
        this.id = aggregateProduct.getId();
        this.parentId = aggregateProduct.getParentId();
        this.previousInterval = aggregateProduct.getInterval();
        this.interval = aggregateProduct.getInterval();
        this.productGroup = aggregateProduct.getProductGroup();
        this.timeStamp = aggregateProduct.getTimeStamp();
        this.quantity = aggregateProduct.getQuantity();
        this.act = aggregateProduct.getAct();
        this.isProcessed = aggregateProduct.isProcessed();
        this.price = aggregateProduct.getPrice();
    }

    public AggregateProduct getAggregateProduct() {
        return new AggregateProduct(this.id, this.parentId, this.previousInterval, this.interval, this.productGroup,
                this.timeStamp, this.quantity, this.act, this.isProcessed, this.price);
    }

    public Long getId_base() {
        return id_base;
    }

    public void setId_base(Long id_base) {
        this.id_base = id_base;
    }

}
