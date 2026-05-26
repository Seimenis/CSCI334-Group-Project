package com.example.occupancy.model;

import java.time.Instant;

public record OccupancyRecord(String recordId, String lotId, int occupiedSpaces, int totalSpaces, Instant timestamp) {
	public double getOccupancyRate() {
		return (double)this.occupiedSpaces / (double)this.totalSpaces;
	}
}
