package com.example.occupancy.service;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

import com.example.occupancy.model.OccupancyRecord;

public class PredictionService {
	/**
	 * Determines the chance that a parking space is occupied at a certain time.
	 * @param records A list of records to base the prediction off.
	 * @param time The time to predict occupancy for.
	 * @return The chance from 0.0 to 1.0 that the relevant parking space is occupied.
	 */
	public double predictOccupancy(List<OccupancyRecord> records, Instant time) {
		ZonedDateTime predictionLocal = time.atZone(ZoneOffset.UTC);
		return records.stream().mapToDouble(record -> {
			ZonedDateTime recordLocal =  record.timestamp().atZone(ZoneOffset.UTC);
			Duration difference = Duration.between(predictionLocal, recordLocal);
			return this.getWeighting(Math.abs(difference.toHoursPart()), Math.abs(difference.toMinutesPart()), Math.abs(difference.toSecondsPart()));
		}).average().orElse(0);
	}
	
	private double getWeighting(int hours, int minutes, int seconds) {
		double hourFraction = ((seconds / 60.0 + minutes) / 60.0 + hours);
		return Math.tanh(-hourFraction) + 1;
	}
}
