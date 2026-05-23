package com.example.adminstats.model.DTO;

import java.time.LocalDate;

public class PeakHourSnapshotDTO {
    private int lotId;
    private LocalDate date;
    private int hour;
    private double occupancyRate;

    public PeakHourSnapshotDTO(int lotId, LocalDate date, int hour, double occupancyRate) {
        this.lotId = lotId;
        this.date = date;
        this.hour = hour;
        this.occupancyRate = occupancyRate;
    }

    public void setLotId(int lotId) { this.lotId = lotId; }
    public void setDate(LocalDate date) { this.date = date; }
    public void setHour(int hour) { this.hour = hour; }
    public void setOccupancyRate(double occupancyRate) { this.occupancyRate = occupancyRate; }

    public int getLotId() { return lotId; }
    public LocalDate getDate() { return date; }
    public int getHour() { return hour; }
    public double getOccupancyRate() { return occupancyRate; }
}
