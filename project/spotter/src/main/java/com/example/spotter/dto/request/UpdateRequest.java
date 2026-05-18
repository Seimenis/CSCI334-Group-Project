package com.example.spotter.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;

public class UpdateRequest {
	private String lotName;
	private String zone;
	private String bayNumber;
	private String sensorId;
	@Min(1)
	private Integer maxParkingMinutes;
	private Boolean disabilityPermitRequired;
	private Boolean isOccupied;
	@DecimalMin("0.0")
	@DecimalMax("1.0")
	private Double confidence;
	private String source;

    public UpdateRequest() {}

    public UpdateRequest(Integer maxParkingMinutes, Boolean disabilityPermitRequired, Boolean isOccupied) {
        this.maxParkingMinutes = maxParkingMinutes;
        this.disabilityPermitRequired = disabilityPermitRequired;
        this.isOccupied = isOccupied;
    }

	public String getLotName() {
		return lotName;
	}

	public void setLotName(String lotName) {
		this.lotName = lotName;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getBayNumber() {
		return bayNumber;
	}

	public void setBayNumber(String bayNumber) {
		this.bayNumber = bayNumber;
	}

	public String getSensorId() {
		return sensorId;
	}

	public void setSensorId(String sensorId) {
		this.sensorId = sensorId;
	}

	public Integer getMaxParkingMinutes() {
		return maxParkingMinutes;
	}

	public void setMaxParkingMinutes(Integer maxParkingMinutes) {
		this.maxParkingMinutes = maxParkingMinutes;
	}

	public Boolean getDisabilityPermitRequired() {
		return disabilityPermitRequired;
	}

	public void setDisabilityPermitRequired(Boolean disabilityPermitRequired) {
		this.disabilityPermitRequired = disabilityPermitRequired;
	}

	public Boolean getIsOccupied() {
		return isOccupied;
	}

	public void setIsOccupied(Boolean isOccupied) {
		this.isOccupied = isOccupied;
	}

	public Double getConfidence() {
		return confidence;
	}

	public void setConfidence(Double confidence) {
		this.confidence = confidence;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
}
