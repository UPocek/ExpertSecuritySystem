package com.ftn.sbnz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.sbnz.model.models.AbstractUser;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<AbstractUser, Long> {

    Optional<AbstractUser> findByUsername(String username);

    boolean existsByUsername(String username);
}
