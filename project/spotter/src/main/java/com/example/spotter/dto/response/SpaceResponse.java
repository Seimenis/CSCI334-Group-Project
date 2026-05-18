package com.example.spotter.dto.response;

import java.time.Instant;

import com.example.spotter.model.Space;

public class SpaceResponse {
	private Long id;
	private String lotName;
	private String zone;
	private String bayNumber;
	private String displayName;
	private String sensorId;
	private int maxParkingMinutes;
	private boolean disabilityPermitRequired;
	private boolean occupied;
	private double confidence;
	private String statusSource;
	private Instant lastUpdated;
	private Double latitude;
	private Double longitude;
	
	public SpaceResponse () {}
	
	public SpaceResponse(long id, int maxParkingMinutes, boolean disabilityPermitRequired, boolean isOccupied) {
		this.id = id;
		this.maxParkingMinutes = maxParkingMinutes;
		this.disabilityPermitRequired = disabilityPermitRequired;
		this.occupied = isOccupied;
	}
	
	public SpaceResponse(Space space) {
		this.id = space.getId();
		this.lotName = space.getLotName();
		this.zone = space.getZone();
		this.bayNumber = space.getBayNumber();
		this.displayName = space.getDisplayName();
		this.sensorId = space.getSensorId();
		this.maxParkingMinutes = space.getMaxParkingMinutes();
		this.disabilityPermitRequired = space.isDisabilityPermitRequired();
		this.occupied = space.isOccupied();
		this.confidence = space.getConfidence();
		this.statusSource = space.getStatusSource();
		this.lastUpdated = space.getLastUpdated();
		this.latitude = space.getLatitude();
		this.longitude = space.getLongitude();
	}

	public Long getId() {
		return id;
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

	public String getDisplayName() {
		return displayName;
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

	public Double getLatitude() {
		return latitude;
	}

	public Double getLongitude() {
		return longitude;
	}
}
