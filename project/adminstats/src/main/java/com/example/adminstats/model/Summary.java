package com.example.adminstats.model;

import com.example.adminstats.model.DTO.OccupancySnapshotDTO;
import com.example.adminstats.model.DTO.PeakHourSnapshotDTO;
import com.example.adminstats.model.DTO.UtilisationSnapshotDTO;

public class Summary {
    private OccupancySnapshotDTO occupancy_snap;
    private PeakHourSnapshotDTO peakHour_snap;
    private UtilisationSnapshotDTO utilisation_snap;

    public void setOccupancySnapshot(OccupancySnapshotDTO _occupancy_snap){this.occupancy_snap = _occupancy_snap;}
    public void setPeakHourSnapshot(PeakHourSnapshotDTO _peakHour_snap){this.peakHour_snap = _peakHour_snap;}
    public void setUtilisationSnapshot(UtilisationSnapshotDTO _utilisation_snap){this.utilisation_snap = _utilisation_snap;}

    public OccupancySnapshotDTO getOccupancySnapshot(){return this.occupancy_snap;}
    public PeakHourSnapshotDTO getPeakHourSnapshot(){return this.peakHour_snap;}
    public UtilisationSnapshotDTO getUtilisationSnapshot(){return this.utilisation_snap;}
}
