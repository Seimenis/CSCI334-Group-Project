package com.example.spotter.repository;

import com.example.spotter.model.Spotter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.springframework.stereotype.Repository;

@Repository
public class SpotterRepository {

    private final ConcurrentMap<String, Spotter> spots = new ConcurrentHashMap<>();

    public List<Spotter> findAll() {
        return spots.values().stream()
                .sorted(Comparator.comparing(Spotter::getSpotId))
                .toList();
    }

    public Optional<Spotter> findBySpotId(String spotId) {
        return Optional.ofNullable(spots.get(spotId));
    }

    public Spotter save(Spotter spotter) {
        spots.put(spotter.getSpotId(), spotter);
        return spotter;
    }

    public List<Spotter> saveAll(List<Spotter> spotters) {
        List<Spotter> savedSpots = new ArrayList<>();
        for (Spotter spotter : spotters) {
            savedSpots.add(save(spotter));
        }
        return savedSpots;
    }
}
