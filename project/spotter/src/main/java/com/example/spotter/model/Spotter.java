package com.example.spotter.model;

import java.time.Instant;

public class Spotter {

    private String spotId;
    private String lotId;
    private boolean occupied;
    private double confidence;
    private String source;
    private Instant lastUpdated;

    public Spotter() {
    }

    public Spotter(String spotId, String lotId, boolean occupied, double confidence, String source, Instant lastUpdated) {
        this.spotId = spotId;
        this.lotId = lotId;
        this.occupied = occupied;
        this.confidence = confidence;
        this.source = source;
        this.lastUpdated = lastUpdated;
    }

    public String getSpotId() {
        return spotId;
    }

    public void setSpotId(String spotId) {
        this.spotId = spotId;
    }

    public String getLotId() {
        return lotId;
    }

    public void setLotId(String lotId) {
        this.lotId = lotId;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Instant getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
