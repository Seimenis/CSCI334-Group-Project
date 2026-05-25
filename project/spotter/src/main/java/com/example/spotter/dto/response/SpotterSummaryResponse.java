package com.example.spotter.dto.response;

import java.util.List;

public class SpotterSummaryResponse {

    private int totalSpaces;
    private int occupiedSpaces;
    private int availableSpaces;
    private int disabilityPermitSpaces;
    private int availableDisabilityPermitSpaces;
    private double occupancyRate;
    private List<ZoneSummaryResponse> zones;

    public SpotterSummaryResponse(
            int totalSpaces,
            int occupiedSpaces,
            int availableSpaces,
            int disabilityPermitSpaces,
            int availableDisabilityPermitSpaces,
            double occupancyRate,
            List<ZoneSummaryResponse> zones) {
        this.totalSpaces = totalSpaces;
        this.occupiedSpaces = occupiedSpaces;
        this.availableSpaces = availableSpaces;
        this.disabilityPermitSpaces = disabilityPermitSpaces;
        this.availableDisabilityPermitSpaces = availableDisabilityPermitSpaces;
        this.occupancyRate = occupancyRate;
        this.zones = zones;
    }

    public int getTotalSpaces() {
        return totalSpaces;
    }

    public int getOccupiedSpaces() {
        return occupiedSpaces;
    }

    public int getAvailableSpaces() {
        return availableSpaces;
    }

    public int getDisabilityPermitSpaces() {
        return disabilityPermitSpaces;
    }

    public int getAvailableDisabilityPermitSpaces() {
        return availableDisabilityPermitSpaces;
    }

    public double getOccupancyRate() {
        return occupancyRate;
    }

    public List<ZoneSummaryResponse> getZones() {
        return zones;
    }
}
