package com.example.adminstats.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Snapshot {
    private int lotId;
    @Id
    private LocalDate date;
    private String occupancy;
    private int spotsTotal;

    public Snapshot() {}

    public Snapshot(Long id, int lotId, LocalDate date, String occupancy, int spotsTotal) {
        this.lotId = lotId;
        this.date = date;
        this.occupancy = occupancy;
        this.spotsTotal = spotsTotal;
    }

    public void setLotId(int _lotId){this.lotId=_lotId;}
    public void setDate(LocalDate _date){this.date=_date;}
    public void setOccupancy(String _occupancy){this.occupancy=_occupancy;}
    public void setSpotsTotal(int _spotsTotal){this.spotsTotal = _spotsTotal;}

    public int getLotId(){return this.lotId;}
    public LocalDate getDate(){return this.date;}
    public String getOccupancy(){return this.occupancy;}
    public int getSpotsTotal(){return this.spotsTotal;}
}

