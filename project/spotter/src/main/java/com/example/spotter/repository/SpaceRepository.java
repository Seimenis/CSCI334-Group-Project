package com.example.spotter.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.spotter.model.Space;

@Repository
public interface SpaceRepository extends JpaRepository<Space, Long> {
	Optional<Space> findBySensorId(String sensorId);

	boolean existsBySensorId(String sensorId);

	List<Space> findByLotNameIgnoreCase(String lotName);

	List<Space> findByZoneIgnoreCase(String zone);
}
