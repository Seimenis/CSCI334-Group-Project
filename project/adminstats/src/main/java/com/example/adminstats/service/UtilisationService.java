package com.example.adminstats.service;

import com.example.adminstats.model.Snapshot;
import com.example.adminstats.model.DTO.UtilisationSnapshotDTO;

public class UtilisationService {
    public UtilisationSnapshotDTO UtilisationSnapshotAssembler(Snapshot snapshot, int start, int end){
        double utilisationRate = -1;
        int[] occupancy_history = snapshot.getOccupancy();

        for(int i = start; i <= end; i++){
            utilisationRate += (occupancy_history[i]/snapshot.getSpotsTotal())*100;
        }

        utilisationRate = utilisationRate / (end - start);

        UtilisationSnapshotDTO us = new UtilisationSnapshotDTO(
            snapshot.getLotId(), snapshot.getDate(), utilisationRate, start, end
        );

        return us;
    }
}
