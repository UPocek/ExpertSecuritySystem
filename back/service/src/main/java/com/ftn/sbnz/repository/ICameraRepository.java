package com.ftn.sbnz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.sbnz.model.models.Camera;

@Repository
public interface ICameraRepository extends JpaRepository<Camera, Long> {
    public List<Camera> findAllByRoomBuildingId(Long id);

}
