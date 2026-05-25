package com.example.spotter.dto.response;

public class SimulationEventResponse {

    private long sequenceNumber;
    private SpaceResponse space;
    private DetectionEventResponse event;

    public SimulationEventResponse(long sequenceNumber, SpaceResponse space, DetectionEventResponse event) {
        this.sequenceNumber = sequenceNumber;
        this.space = space;
        this.event = event;
    }

    public long getSequenceNumber() {
        return sequenceNumber;
    }

    public SpaceResponse getSpace() {
        return space;
    }

    public DetectionEventResponse getEvent() {
        return event;
    }
}
