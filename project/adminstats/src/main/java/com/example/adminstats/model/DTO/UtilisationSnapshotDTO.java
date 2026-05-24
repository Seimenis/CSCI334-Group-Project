package com.example.adminstats.model.DTO;

import java.time.LocalDate;

public class UtilisationSnapshotDTO {
    private int lotId;
    private LocalDate date;
    private double utilisationRate;

    public UtilisationSnapshotDTO(int lotId, LocalDate date, double utilisationRate) {
        this.lotId = lotId;
        this.date = date;
        this.utilisationRate = utilisationRate;
    }

    public void setLotId(int lotId) { this.lotId = lotId; }
    public void setDate(LocalDate date) { this.date = date; }
    public void setUtilisationRate(double utilisationRate) { this.utilisationRate = utilisationRate; }

    public int getLotId() { return lotId; }
    public LocalDate getDate() { return date; }
    public double getUtilisationRate() { return utilisationRate; }

    @Override
    public String toString(){
        return "Daily Utilisation: \n" +
        "Overall: " + utilisationRate + "%\n";
    }
}
