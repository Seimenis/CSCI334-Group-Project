package com.example.adminstats.model;

import java.util.*;

public class Snapshot {
    private Long id;
    private int lotId;
    private String date;
    private int[] occupancy = new int[24]; // empty / not recorded hours become -1
    private int spotsTotal;

    
    public void setLotId(int _lotId){this.lotId=_lotId;}
    public void setDate(String _date){this.date=_date;}
    public void setOccupancy(int[] _occupancy){this.occupancy=_occupancy;}
    public void setSpotsTotal(int _spotsTotal){this.spotsTotal = _spotsTotal;}

    public int getLotId(){return this.lotId;}
    public String getDate(){return this.date;}
    public int[] getOccupancy(){return this.occupancy;}
    public int getSpotsTotal(){return this.spotsTotal;}
}

