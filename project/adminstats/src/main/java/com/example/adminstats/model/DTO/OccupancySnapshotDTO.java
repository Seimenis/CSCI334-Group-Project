package com.example.adminstats.model.DTO;

import java.time.LocalDate;

public class OccupancySnapshotDTO {
    private int lotId;
    private LocalDate date;
    private int hour;
    private int spotsTaken;
    private int spotsTotal;

    public OccupancySnapshotDTO(int lotId, LocalDate date, int hour, int spotsTaken, int spotsTotal) {
        this.lotId = lotId;
        this.date = date;
        this.hour = hour;
        this.spotsTaken = spotsTaken;
        this.spotsTotal = spotsTotal;
    }

    public void setLotId(int lotId) { this.lotId = lotId; }
    public void setDate(LocalDate date) { this.date = date; }
    public void setHour(int hour) { this.hour = hour; }
    public void setSpotsTaken(int spotsTaken) { this.spotsTaken = spotsTaken; }
    public void setSpotsTotal(int spotsTotal) { this.spotsTotal = spotsTotal; }

    public int getLotId() { return lotId; }
    public LocalDate getDate() { return date; }
    public int getHour() { return hour; }
    public int getSpotsTaken() { return spotsTaken; }
    public int getSpotsTotal() { return spotsTotal; }

    @Override
    public String toString(){
        return "Last Recorded Occupancy: \n" +
        "Hour: " + hour + ":00\n" + 
        "Occupants: " + spotsTaken + "\n" + 
        "Capacity: " + spotsTotal + "\n";
    }
}
