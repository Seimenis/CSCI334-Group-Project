package com.example.spotter.dto.response;

import java.util.List;

public class SimulationRunResponse {

    private int appliedEvents;
    private int feedSize;
    private int nextFeedIndex;
    private List<SimulationEventResponse> events;
    private SpotterSummaryResponse summary;

    public SimulationRunResponse(
            int appliedEvents,
            int feedSize,
            int nextFeedIndex,
            List<SimulationEventResponse> events,
            SpotterSummaryResponse summary) {
        this.appliedEvents = appliedEvents;
        this.feedSize = feedSize;
        this.nextFeedIndex = nextFeedIndex;
        this.events = events;
        this.summary = summary;
    }

    public int getAppliedEvents() {
        return appliedEvents;
    }

    public int getFeedSize() {
        return feedSize;
    }

    public int getNextFeedIndex() {
        return nextFeedIndex;
    }

    public List<SimulationEventResponse> getEvents() {
        return events;
    }

    public SpotterSummaryResponse getSummary() {
        return summary;
    }
}
