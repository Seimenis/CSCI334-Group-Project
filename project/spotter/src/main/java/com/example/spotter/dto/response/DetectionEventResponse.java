package com.example.spotter.dto.response;

import java.time.Instant;

import com.example.spotter.model.DetectionEvent;

public class DetectionEventResponse {

    private Long id;
    private Long spaceId;
    private String sensorId;
    private String lotName;
    private String zone;
    private String bayNumber;
    private boolean previousOccupied;
    private boolean occupied;
    private double confidence;
    private String source;
    private Instant detectedAt;

    public DetectionEventResponse(DetectionEvent event) {
        this.id = event.getId();
        this.spaceId = event.getSpaceId();
        this.sensorId = event.getSensorId();
        this.lotName = event.getLotName();
        this.zone = event.getZone();
        this.bayNumber = event.getBayNumber();
        this.previousOccupied = event.isPreviousOccupied();
        this.occupied = event.isOccupied();
        this.confidence = event.getConfidence();
        this.source = event.getSource();
        this.detectedAt = event.getDetectedAt();
    }

    public Long getId() {
        return id;
    }

    public Long getSpaceId() {
        return spaceId;
    }

    public String getSensorId() {
        return sensorId;
    }

    public String getLotName() {
        return lotName;
    }

    public String getZone() {
        return zone;
    }

    public String getBayNumber() {
        return bayNumber;
    }

    public boolean isPreviousOccupied() {
        return previousOccupied;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public double getConfidence() {
        return confidence;
    }

    public String getSource() {
        return source;
    }

    public Instant getDetectedAt() {
        return detectedAt;
    }
}
