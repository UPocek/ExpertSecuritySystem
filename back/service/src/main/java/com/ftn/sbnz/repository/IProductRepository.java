package com.ftn.sbnz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ftn.sbnz.model.models.Product;

public interface IProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE p.isContainedIn = ?1")
    List<Product> findByIsContainedIn(Product product);

    List<Product> findAllByplacedInId(Long roomId);

}
