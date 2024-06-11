package com.ftn.sbnz.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.sbnz.model.models.ContinuousSensor;

@Repository
public interface IContinuousSensorRepository extends JpaRepository<ContinuousSensor, Long> {
    public boolean existsById(Long id);

    public Optional<ContinuousSensor> findById(Long id);

    public List<ContinuousSensor> findAllByRoomBuildingId(Long id);
}
