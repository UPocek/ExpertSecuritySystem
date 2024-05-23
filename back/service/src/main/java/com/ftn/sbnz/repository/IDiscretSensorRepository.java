package com.ftn.sbnz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.sbnz.model.models.DiscretSensor;

@Repository
public interface IDiscretSensorRepository extends JpaRepository<DiscretSensor, Long> {

}
