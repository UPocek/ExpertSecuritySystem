package com.ftn.sbnz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.sbnz.model.models.ProductAggregationToStore;

public interface IProductAggregationsRepository extends JpaRepository<ProductAggregationToStore, Long> {

}
