package com.ftn.sbnz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.sbnz.model.models.ContinuousSensor;

@Repository
public interface IContinuousSensorRepository extends JpaRepository<ContinuousSensor, Long> {
    // You can define custom query methods here if needed
}
