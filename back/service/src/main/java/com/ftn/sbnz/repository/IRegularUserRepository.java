package com.ftn.sbnz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.sbnz.model.models.RegularUser;

public interface IRegularUserRepository extends JpaRepository<RegularUser, Long> {

}
