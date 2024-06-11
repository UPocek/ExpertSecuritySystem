package com.ftn.sbnz.model.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String name;
    @ManyToOne
    public Product isContainedIn;
    @ManyToOne
    public Room placedIn;

    public Product() {
    }

    public Product(String name) {
        this.name = name;
    }

    public Product(String name, Product isContainedIn) {
        this.name = name;
        this.isContainedIn = isContainedIn;
    }

    public Product(String name, Product isContainedIn, Room placedIn) {
        this.name = name;
        this.isContainedIn = isContainedIn;
        this.placedIn = placedIn;
    }

    public Product(String name, Room placedIn) {
        this.name = name;
        this.placedIn = placedIn;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Product getIsContainedIn() {
        return isContainedIn;
    }

    public void setIsContainedIn(Product isContainedIn) {
        this.isContainedIn = isContainedIn;
    }

    public Room getPlacedIn() {
        return placedIn;
    }

    public void setPlacedIn(Room placedIn) {
        this.placedIn = placedIn;
    }

}
