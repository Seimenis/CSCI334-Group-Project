package com.example.spotter.dto.event;

import java.time.Instant;
import java.util.UUID;

public class EventMetadata {
    private final String eventId = UUID.randomUUID().toString();
    private final Instant timestamp = Instant.now();
    private final String serviceName = "spotter-service";

    public EventMetadata() {}

    public String getEventId() {
        return eventId;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public String getServiceName() {
        return serviceName;
    }
}
