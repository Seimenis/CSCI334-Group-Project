package com.example.spotter.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.spotter.model.SimulationFeedRecord;

import jakarta.annotation.PostConstruct;

@Service
public class SimulationFeedService {

    private final SpotterDatasetService datasetService;
    private List<SimulationFeedRecord> feed = new ArrayList<>();
    private int cursor = 0;

    public SimulationFeedService(SpotterDatasetService datasetService) {
        this.datasetService = datasetService;
    }

    @PostConstruct
    public void reload() {
        this.feed = datasetService.loadSimulationFeed();
        this.cursor = 0;
    }

    public synchronized List<SimulationFeedRecord> nextEvents(int count) {
        List<SimulationFeedRecord> records = new ArrayList<>();
        if (feed.isEmpty()) {
            return records;
        }
        for (int index = 0; index < count; index++) {
            if (cursor >= feed.size()) {
                cursor = 0;
            }
            records.add(feed.get(cursor));
            cursor++;
        }
        return records;
    }

    public synchronized void reset() {
        cursor = 0;
    }

    public int getFeedSize() {
        return feed.size();
    }

    public synchronized int getNextFeedIndex() {
        return cursor;
    }
}
