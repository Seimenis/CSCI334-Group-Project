package com.example.occupancy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.occupancy.model.OccupancyRecord;

@Repository
public interface OccupancyRepository extends JpaRepository<OccupancyRecord, String> {
    public OccupancyRecord findCurrentByLotId(String lotId);
    
    public List<OccupancyRecord> findHistoryByLotId(String lotId);
    
    public OccupancyRecord save(OccupancyRecord record);
}
