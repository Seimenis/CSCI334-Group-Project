package com.example.spotter.dto.event;

import java.time.Instant;

import com.example.spotter.model.Space;

public class SpaceUpdatedEvent {
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
	private double confidence;
	private String statusSource;
	private Instant lastUpdated;
	
	public SpaceUpdatedEvent() {
	}
	
	public SpaceUpdatedEvent(long id, int maxParkingMinutes, boolean disabilityPermitRequired, boolean isOccupied) {
		this.id = id;
		this.maxParkingMinutes = maxParkingMinutes;
		this.disabilityPermitRequired = disabilityPermitRequired;
		this.occupied = isOccupied;
	}
	
	public SpaceUpdatedEvent(Space space) {
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
		this.confidence = space.getConfidence();
		this.statusSource = space.getStatusSource();
		this.lastUpdated = space.getLastUpdated();
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

	public double getConfidence() {
		return confidence;
	}

	public String getStatusSource() {
		return statusSource;
	}

	public Instant getLastUpdated() {
		return lastUpdated;
	}

	public EventMetadata getMetadata() {
		return metadata;
	}
}
