package com.ftn.sbnz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ftn.sbnz.model.models.Room;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoomRepository extends JpaRepository<Room, Long> {

}
