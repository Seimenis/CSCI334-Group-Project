package com.example.spotter.controller;

import com.example.spotter.model.DetectionRequest;
import com.example.spotter.model.SimulationRequest;
import com.example.spotter.model.Spotter;
import com.example.spotter.model.SpotterSummary;
import com.example.spotter.service.SpotterService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/spotter")
public class SpotterController {

    private final SpotterService spotterService;

    public SpotterController(SpotterService spotterService) {
        this.spotterService = spotterService;
    }

    @GetMapping("/health")
    public String health() {
        return "spotter service running";
    }

    @GetMapping("/spots")
    public List<Spotter> getAllSpots() {
        return spotterService.getAllSpots();
    }

    @GetMapping("/spots/{spotId}")
    public Spotter getSpot(@PathVariable String spotId) {
        return spotterService.getSpot(spotId);
    }

    @PostMapping("/spots/{spotId}/detect")
    public Spotter detectSpot(
            @PathVariable String spotId,
            @Valid @RequestBody DetectionRequest request) {
        return spotterService.recordDetection(spotId, request);
    }

    @PostMapping("/simulate")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Spotter> simulate(@Valid @RequestBody SimulationRequest request) {
        return spotterService.simulateLot(request);
    }

    @GetMapping("/summary")
    public SpotterSummary getSummary() {
        return spotterService.getSummary();
    }
}
