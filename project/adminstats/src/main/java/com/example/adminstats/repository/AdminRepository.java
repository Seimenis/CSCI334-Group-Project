package com.example.adminstats.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import  com.example.adminstats.model.Snapshot;

public interface AdminRepository extends JpaRepository<Snapshot, Long>{
    Snapshot findSnapshotByDate(String date);
}
