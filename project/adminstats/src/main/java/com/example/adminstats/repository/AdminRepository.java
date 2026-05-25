package com.example.adminstats.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import  com.example.adminstats.model.Snapshot;

@Repository
public interface AdminRepository extends JpaRepository<Snapshot, Long>{
    Snapshot findSnapshotByDate(LocalDate date);
}
