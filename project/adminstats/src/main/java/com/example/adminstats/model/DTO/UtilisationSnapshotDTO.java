package com.example.adminstats.model.DTO;

public class UtilisationSnapshotDTO {
    private int lotId;
    private String date;
    private double utilisationRate;
    private int periodStart;
    private int periodEnd;

    public UtilisationSnapshotDTO(int lotId, String date, double utilisationRate, int periodStart, int periodEnd) {
        this.lotId = lotId;
        this.date = date;
        this.utilisationRate = utilisationRate;
        this.periodStart = periodStart;
        this.periodEnd = periodEnd;
    }

    public void setLotId(int lotId) { this.lotId = lotId; }
    public void setDate(String date) { this.date = date; }
    public void setUtilisationRate(double utilisationRate) { this.utilisationRate = utilisationRate; }
    public void setPeriodStart(int periodStart) { this.periodStart = periodStart; }
    public void setPeriodEnd(int periodEnd) { this.periodEnd = periodEnd; }

    public int getLotId() { return lotId; }
    public String getDate() { return date; }
    public double getUtilisationRate() { return utilisationRate; }
    public int getPeriodStart() { return periodStart; }
    public int getPeriodEnd() { return periodEnd; }
}
