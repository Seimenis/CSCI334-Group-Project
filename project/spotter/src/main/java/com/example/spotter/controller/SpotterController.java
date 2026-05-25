package com.example.spotter.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.spotter.dto.request.DetectionRequest;
import com.example.spotter.dto.request.SimulationRequest;
import com.example.spotter.dto.request.UpdateRequest;
import com.example.spotter.dto.response.DetectionEventResponse;
import com.example.spotter.dto.response.SimulationRunResponse;
import com.example.spotter.dto.response.SpaceResponse;
import com.example.spotter.dto.response.SpotterSummaryResponse;
import com.example.spotter.dto.response.ZoneSummaryResponse;
import com.example.spotter.service.SpotterService;

import jakarta.validation.Valid;

@RestController
@RequestMapping({"/spotter", "/api/spotter"})
public class SpotterController {

    private final SpotterService spotterService;

    public SpotterController(SpotterService spotterService) {
        this.spotterService = spotterService;
    }

    @GetMapping("/health")
    public String health() {
        return "spotter service running";
    }

    @GetMapping("/spaces")
    public List<SpaceResponse> getSpaces(
            @RequestParam(required = false) String lotName,
            @RequestParam(required = false) String zone,
            @RequestParam(required = false) Boolean occupied,
            @RequestParam(required = false) Boolean disabilityPermitRequired) {
        return spotterService.getSpaces(lotName, zone, occupied, disabilityPermitRequired);
    }

    @GetMapping("/lots")
    public List<String> getLots() {
        return spotterService.getLots();
    }

    @GetMapping("/zones")
    public List<ZoneSummaryResponse> getZones(@RequestParam(required = false) String lotName) {
        return spotterService.getZones(lotName);
    }

    @GetMapping("/spaces/{spaceId}")
    public SpaceResponse getSpace(@PathVariable Long spaceId) {
        return spotterService.getSpace(spaceId);
    }

    @GetMapping("/sensors/{sensorId}")
    public SpaceResponse getSpaceBySensorId(@PathVariable String sensorId) {
        return spotterService.getSpaceBySensorId(sensorId);
    }

    @PatchMapping("/spaces/{spaceId}")
    public SpaceResponse updateSpace(
            @PathVariable Long spaceId,
            @Valid @RequestBody UpdateRequest request) {
        return spotterService.update(request, spaceId);
    }

    @PostMapping("/spaces/{spaceId}/detect")
    @ResponseStatus(HttpStatus.CREATED)
    public DetectionEventResponse recordDetection(
            @PathVariable Long spaceId,
            @Valid @RequestBody DetectionRequest request,
            @RequestParam(defaultValue = "true") boolean publishEvent) {
        return spotterService.recordDetection(spaceId, request, publishEvent);
    }

    @PostMapping("/sensors/{sensorId}/detect")
    @ResponseStatus(HttpStatus.CREATED)
    public DetectionEventResponse recordSensorDetection(
            @PathVariable String sensorId,
            @Valid @RequestBody DetectionRequest request,
            @RequestParam(defaultValue = "true") boolean publishEvent) {
        return spotterService.recordSensorDetection(sensorId, request, publishEvent);
    }

    @GetMapping("/summary")
    public SpotterSummaryResponse getSummary(
            @RequestParam(required = false) String lotName,
            @RequestParam(required = false) String zone) {
        return spotterService.getSummary(lotName, zone);
    }

    @GetMapping("/events")
    public List<DetectionEventResponse> getRecentEvents() {
        return spotterService.getRecentEvents();
    }

    @PostMapping("/simulation/run")
    public SimulationRunResponse runSimulation(@Valid @RequestBody SimulationRequest request) {
        return spotterService.runSimulation(request);
    }

    @PostMapping("/simulation/next")
    public SimulationRunResponse runNextSimulationEvent() {
        SimulationRequest request = new SimulationRequest();
        request.setEventCount(1);
        return spotterService.runSimulation(request);
    }

    @PostMapping("/simulation/reset")
    public SimulationRunResponse resetSimulation() {
        return spotterService.resetSimulation();
    }
}
