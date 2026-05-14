package com.example.spotter.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Space {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long _id;
	private int _maxParkingMinutes;
	private boolean _disabilityPermitRequired;
	private boolean _isOccupied;
	
	public Space(int maxParkingMinutes, boolean disabilityPermitRequired) {
		_maxParkingMinutes = maxParkingMinutes;
		setDisabilityPermitRequired(disabilityPermitRequired);
		// It should be a non-issue to assume a newly-constructed space hasn't
		// immediately been taken by a car. Right?
		_isOccupied = false;
	}
	
	public Long getId() {
		return this._id;
	}
	
	public int getMaxParkingMinutes() {
		return _maxParkingMinutes;
	}
	public void setMaxParkingMinutes(int to) {
		_maxParkingMinutes = to;
	}
	/**
	 * @return the _disabilityPermitRequired
	 */
	public boolean isDisabilityPermitRequired() {
		return _disabilityPermitRequired;
	}

	/**
	 * @param _disabilityPermitRequired the _disabilityPermitRequired to set
	 */
	public void setDisabilityPermitRequired(boolean _disabilityPermitRequired) {
		this._disabilityPermitRequired = _disabilityPermitRequired;
	}

	public boolean isOccupied() {
		return _isOccupied;
	}
	public void setOccupied(boolean to) {
		_isOccupied = to;
	}
}
