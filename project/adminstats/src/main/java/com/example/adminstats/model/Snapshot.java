package com.example.adminstats.model;

import java.util.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Snapshot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int lotId;
    private LocalDate date;
    private int[] occupancy = new int[24]; // empty / not recorded hours become -1
    private int spotsTotal;

    public Snapshot() {}

    public Snapshot(Long id, int lotId, LocalDate date, int[] occupancy, int spotsTotal) {
        this.id = id;
        this.lotId = lotId;
        this.date = date;
        this.occupancy = occupancy;
        this.spotsTotal = spotsTotal;
    }

    public void setLotId(int _lotId){this.lotId=_lotId;}
    public void setDate(LocalDate _date){this.date=_date;}
    public void setOccupancy(int[] _occupancy){this.occupancy=_occupancy;}
    public void setSpotsTotal(int _spotsTotal){this.spotsTotal = _spotsTotal;}

    public int getLotId(){return this.lotId;}
    public LocalDate getDate(){return this.date;}
    public int[] getOccupancy(){return this.occupancy;}
    public int getSpotsTotal(){return this.spotsTotal;}
}

