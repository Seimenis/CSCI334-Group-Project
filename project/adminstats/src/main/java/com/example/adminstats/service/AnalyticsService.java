package com.example.adminstats.service;

import com.example.adminstats.repository.*;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.example.adminstats.model.*;
import com.example.adminstats.model.DTO.OccupancySnapshotDTO;
import com.example.adminstats.model.DTO.PeakHourSnapshotDTO;
import com.example.adminstats.model.DTO.UtilisationSnapshotDTO;

@Service
public class AnalyticsService {
    private OccupancyService occupancyService;
    private PeakHourService peakHourService;
    private UtilisationService utilisationService;
    private AdminRepository adminRepo;

    public AnalyticsService(OccupancyService occupancyService, PeakHourService peakHourService, UtilisationService utilisationService, AdminRepository adminRepo){
        this.occupancyService = occupancyService;
        this.peakHourService = peakHourService;
        this.utilisationService = utilisationService;
        this.adminRepo = adminRepo;
    }

    public Summary getSummary(LocalDate date){

        Snapshot snapshot = adminRepo.findSnapshotByDate(date);

        OccupancySnapshotDTO os = occupancyService.OccupancySnapshotAssembler(snapshot);
        PeakHourSnapshotDTO phs = peakHourService.PeakHourSnapshotAssembler(snapshot);
        UtilisationSnapshotDTO us = utilisationService.UtilisationSnapshotAssembler(snapshot);

        Summary summary = new Summary(os, phs, us);

        return summary;
    }
}
