package com.example.spotter.service.dto.event;

import com.example.spotter.dto.event.EventMetadata;
import com.example.spotter.model.Space;

public class SpaceCreatedEvent {
    private final EventMetadata metadata = new EventMetadata();
    private Long id;
    private String lotName;
    private String parkingLot;
    private String zone;
    private String bayNumber;
    private String parkingSpace;
    private String sensorId;
    private int maxParkingMinutes;
    private boolean disabilityPermitRequired;
    private boolean occupied;

    public SpaceCreatedEvent() {
    }

    public SpaceCreatedEvent(Space space) {
        this.id = space.getId();
        this.lotName = space.getLotName();
        this.parkingLot = space.getLotName();
        this.zone = space.getZone();
        this.bayNumber = space.getBayNumber();
        this.parkingSpace = space.getDisplayName();
        this.sensorId = space.getSensorId();
        this.maxParkingMinutes = space.getMaxParkingMinutes();
        this.disabilityPermitRequired = space.isDisabilityPermitRequired();
        this.occupied = space.isOccupied();
    }

    public EventMetadata getMetadata() {
        return metadata;
    }

    public Long getId() {
        return id;
    }

    public String getLotName() {
        return lotName;
    }

    public String getParkingLot() {
        return parkingLot;
    }

    public String getZone() {
        return zone;
    }

    public String getBayNumber() {
        return bayNumber;
    }

    public String getParkingSpace() {
        return parkingSpace;
    }

    public String getSensorId() {
        return sensorId;
    }

    public int getMaxParkingMinutes() {
        return maxParkingMinutes;
    }

    public boolean isDisabilityPermitRequired() {
        return disabilityPermitRequired;
    }

    public boolean isOccupied() {
        return occupied;
    }
}
