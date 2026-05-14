package com.example.spotter.dto.response;

import com.example.spotter.model.Space;

public class SpaceResponse {
	private long id;
	private int maxParkingMinutes;
	private boolean disabilityPermitRequired;
	private boolean isOccupied;
	
	public SpaceResponse () {}
	
	public SpaceResponse(long id, int maxParkingMinutes, boolean disabilityPermitRequired, boolean isOccupied) {
		this.id = id;
		this.maxParkingMinutes = maxParkingMinutes;
		this.disabilityPermitRequired = disabilityPermitRequired;
		this.isOccupied = isOccupied;
	}
	
	public SpaceResponse(Space space) {
		this(space.getId(), space.getMaxParkingMinutes(), space.isDisabilityPermitRequired(), space.isOccupied());
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @return the maxParkingMinutes
	 */
	public int getMaxParkingMinutes() {
		return maxParkingMinutes;
	}

	/**
	 * @return the disabilityPermitRequired
	 */
	public boolean isDisabilityPermitRequired() {
		return disabilityPermitRequired;
	}

	/**
	 * @return the isOccupied
	 */
	public boolean isOccupied() {
		return isOccupied;
	}
}
