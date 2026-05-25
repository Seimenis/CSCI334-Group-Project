package com.example.spotter.model;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class DetectionEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public DetectionEvent() {
    }

    public DetectionEvent(
            Space space,
            boolean previousOccupied,
            boolean occupied,
            double confidence,
            String source,
            Instant detectedAt) {
        this.spaceId = space.getId();
        this.sensorId = space.getSensorId();
        this.lotName = space.getLotName();
        this.zone = space.getZone();
        this.bayNumber = space.getBayNumber();
        this.previousOccupied = previousOccupied;
        this.occupied = occupied;
        this.confidence = confidence;
        this.source = source;
        this.detectedAt = detectedAt;
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
