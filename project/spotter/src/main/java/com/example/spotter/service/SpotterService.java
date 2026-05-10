package com.example.spotter.service;

import com.example.spotter.model.DetectionRequest;
import com.example.spotter.model.SimulationRequest;
import com.example.spotter.model.Spotter;
import com.example.spotter.model.SpotterSummary;
import com.example.spotter.repository.SpotterRepository;
import jakarta.annotation.PostConstruct;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;

@Service
public class SpotterService {

    private static final String DEFAULT_LOT_ID = "UOW-MAIN";
    private static final int DEFAULT_SPOT_COUNT = 20;

    private final SpotterRepository spotterRepository;

    public SpotterService(SpotterRepository spotterRepository) {
        this.spotterRepository = spotterRepository;
    }

    @PostConstruct
    void seedDefaultSpots() {
        if (spotterRepository.findAll().isEmpty()) {
            simulateLot(new SimulationRequest(DEFAULT_LOT_ID, DEFAULT_SPOT_COUNT, 0));
        }
    }

    public List<Spotter> getAllSpots() {
        return spotterRepository.findAll();
    }

    public Spotter getSpot(String spotId) {
        return spotterRepository.findBySpotId(spotId)
                .orElseThrow(() -> new NoSuchElementException("No spot exists with id " + spotId));
    }

    public Spotter recordDetection(String spotId, DetectionRequest request) {
        String lotId = spotterRepository.findBySpotId(spotId)
                .map(Spotter::getLotId)
                .orElse(DEFAULT_LOT_ID);

        Spotter spotter = new Spotter(
                spotId,
                lotId,
                request.occupied(),
                request.confidence(),
                request.source(),
                Instant.now());

        return spotterRepository.save(spotter);
    }

    public List<Spotter> simulateLot(SimulationRequest request) {
        List<Spotter> simulatedSpots = new ArrayList<>();
        int occupiedSpots = Math.min(request.occupiedSpots(), request.totalSpots());

        for (int index = 1; index <= request.totalSpots(); index++) {
            String spotId = request.lotId() + "-" + String.format("%03d", index);
            boolean occupied = index <= occupiedSpots;
            simulatedSpots.add(new Spotter(
                    spotId,
                    request.lotId(),
                    occupied,
                    1.0,
                    "simulation",
                    Instant.now()));
        }

        return spotterRepository.saveAll(simulatedSpots);
    }

    public SpotterSummary getSummary() {
        List<Spotter> spots = spotterRepository.findAll();
        long occupiedSpots = spots.stream().filter(Spotter::isOccupied).count();
        return new SpotterSummary(spots.size(), (int) occupiedSpots, spots.size() - (int) occupiedSpots);
    }
}
