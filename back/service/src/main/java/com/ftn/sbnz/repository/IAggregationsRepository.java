package com.ftn.sbnz.repository;

import com.ftn.sbnz.model.models.AggregationToStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAggregationsRepository extends JpaRepository<AggregationToStore, Long> {

}
