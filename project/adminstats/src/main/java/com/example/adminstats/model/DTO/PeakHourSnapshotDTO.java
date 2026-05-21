package com.example.adminstats.model.DTO;

public class PeakHourSnapshotDTO {
    private int lotId;
    private String date;
    private int hour;
    private double occupancyRate;

    public PeakHourSnapshotDTO(int lotId, String date, int hour, double occupancyRate) {
        this.lotId = lotId;
        this.date = date;
        this.hour = hour;
        this.occupancyRate = occupancyRate;
    }

    public void setLotId(int lotId) { this.lotId = lotId; }
    public void setDate(String date) { this.date = date; }
    public void setHour(int hour) { this.hour = hour; }
    public void setOccupancyRate(double occupancyRate) { this.occupancyRate = occupancyRate; }

    public int getLotId() { return lotId; }
    public String getDate() { return date; }
    public int getHour() { return hour; }
    public double getOccupancyRate() { return occupancyRate; }
}
