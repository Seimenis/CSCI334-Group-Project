package com.example.spotter.dto.request;

public class UpdateRequest {
	private Integer maxParkingMinutes;
	private Boolean disabilityPermitRequired;
	private Boolean isOccupied;

    public UpdateRequest() {}

    public UpdateRequest(Integer maxParkingMinutes, Boolean disabilityPermitRequired, Boolean isOccupied) {
        this.maxParkingMinutes = maxParkingMinutes;
        this.disabilityPermitRequired = disabilityPermitRequired;
        this.isOccupied = isOccupied;
    }

	/**
	 * @return the maxParkingMinutes
	 */
	public Integer getMaxParkingMinutes() {
		return maxParkingMinutes;
	}

	/**
	 * @return the disabilityPermitRequired
	 */
	public Boolean getDisabilityPermitRequired() {
		return disabilityPermitRequired;
	}

	/**
	 * @return the isOccupied
	 */
	public Boolean getIsOccupied() {
		return isOccupied;
	}
}
