package com.ftn.sbnz.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ftn.sbnz.model.models.Room;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoomRepository extends JpaRepository<Room, Long> {
    @Query("SELECT r FROM Room r WHERE r.isContainedIn = ?1")
    List<Room> findByIsContainedIn(Room room);

    List<Room> findAllByBuildingId(Long id);

    Optional<Room> findByName(String name);
}
