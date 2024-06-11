package com.ftn.sbnz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ftn.sbnz.model.models.DiscretSensor;

@Repository
public interface IDiscretSensorRepository extends JpaRepository<DiscretSensor, Long> {
    public List<DiscretSensor> findAllByRoomBuildingId(Long id);

}
