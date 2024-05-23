package com.ftn.sbnz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.sbnz.model.models.Security;

@Repository
public interface ISecurityRepository extends JpaRepository<Security, Long> {

}