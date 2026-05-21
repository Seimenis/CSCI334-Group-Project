package com.example.adminstats.service;

import com.example.adminstats.model.Snapshot;
import com.example.adminstats.model.DTO.*;

public class PeakHourService {
    public PeakHourSnapshotDTO PeakHourSnapshotAssembler(Snapshot snapshot){
        int hour = -1;
        double occupancyRate = -1;
        int[] occupancy_history = snapshot.getOccupancy();
        int maxOccupancy = -1;

        for(int i = 0; i < 24; i++){
            if(occupancy_history[i] > maxOccupancy){
                maxOccupancy = occupancy_history[i];
                hour = i;
            }
        }

        if(maxOccupancy >= 0 && snapshot.getSpotsTotal() > 0){
            occupancyRate = (double) maxOccupancy / snapshot.getSpotsTotal();
        }

        PeakHourSnapshotDTO phs = new PeakHourSnapshotDTO(
            snapshot.getLotId(), snapshot.getDate(), hour, occupancyRate
        );

        return phs;
    }
}
