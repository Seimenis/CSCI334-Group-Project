package com.example.spotter.model;

import java.time.Instant;

public class SimulationFeedRecord {

    private final long sequenceNumber;
    private final String sensorId;
    private final boolean occupied;
    private final double confidence;
    private final String source;
    private final Instant observedAt;

    public SimulationFeedRecord(
            long sequenceNumber,
            String sensorId,
            boolean occupied,
            double confidence,
            String source,
            Instant observedAt) {
        this.sequenceNumber = sequenceNumber;
        this.sensorId = sensorId;
        this.occupied = occupied;
        this.confidence = confidence;
        this.source = source;
        this.observedAt = observedAt;
    }

    public long getSequenceNumber() {
        return sequenceNumber;
    }

    public String getSensorId() {
        return sensorId;
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

    public Instant getObservedAt() {
        return observedAt;
    }
}
