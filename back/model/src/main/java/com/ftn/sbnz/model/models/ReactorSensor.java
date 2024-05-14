package com.ftn.sbnz.model.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class ReactorSensor {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    public ReactorSensor() {
    }

    public void react() {

    }

    public Long getId() {
        return id;
    }
}
