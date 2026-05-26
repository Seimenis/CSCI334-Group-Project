package com.example.occupancy.controller;

import java.time.Instant;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.occupancy.service.OccupancyService;

@RestController
@RequestMapping({"/occupancy", "/api/occupancy"})
public class OccupancyController {
    private OccupancyService occupancyService;

	public OccupancyController(OccupancyService occupancyService) {
		this.occupancyService = occupancyService;
    }
	
	@PatchMapping("/{lotId}")
	public int getCurrentOccupancy(String lotId) {
		return this.occupancyService.calculateCurrentOccupancy(lotId);
	}
	
	@PatchMapping("/{lotId}/history")
	public auto getOccupancyHistory(String lotId) {
		todo;
	}
	
	@PatchMapping("/{lotId}/predict")
	public double getPredictedOccupancy(String lotId, Instant time) {
		return this.occupancyService.predictFutureOccupancy(lotId, time);
	}
}
