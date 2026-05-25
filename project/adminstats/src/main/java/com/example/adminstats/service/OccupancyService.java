package com.example.adminstats.service;

import org.springframework.stereotype.Service;

import com.example.adminstats.model.Snapshot;
import com.example.adminstats.model.DTO.*;

@Service
public class OccupancyService {
    public OccupancySnapshotDTO OccupancySnapshotAssembler(Snapshot snapshot){
        int hour = -1;
        int occupancy = -1;
        int[] occupancy_history = snapshot.getOccupancy();

        for(int i = 0; i < 24; i++){
            if(occupancy_history[i] == -1){
                hour = i-1;
                occupancy = occupancy_history[i-1];
            }
            else{
                hour = i;
                occupancy = occupancy_history[i];
            }
        };

        OccupancySnapshotDTO os = new OccupancySnapshotDTO(
            snapshot.getLotId(), snapshot.getDate(), hour, occupancy, snapshot.getSpotsTotal()
        );

        return os;
    }
}
