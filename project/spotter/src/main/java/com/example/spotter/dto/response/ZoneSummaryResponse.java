package com.example.spotter.dto.response;

public class ZoneSummaryResponse {

    private String lotName;
    private String zone;
    private int totalSpaces;
    private int occupiedSpaces;
    private int availableSpaces;
    private double occupancyRate;

    public ZoneSummaryResponse(
            String lotName,
            String zone,
            int totalSpaces,
            int occupiedSpaces,
            int availableSpaces,
            double occupancyRate) {
        this.lotName = lotName;
        this.zone = zone;
        this.totalSpaces = totalSpaces;
        this.occupiedSpaces = occupiedSpaces;
        this.availableSpaces = availableSpaces;
        this.occupancyRate = occupancyRate;
    }

    public String getLotName() {
        return lotName;
    }

    public String getZone() {
        return zone;
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

    public double getOccupancyRate() {
        return occupancyRate;
    }
}
