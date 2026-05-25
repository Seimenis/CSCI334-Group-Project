package com.example.spotter.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record SimulationRequest(
        @NotBlank String lotId,
        @Min(1) int totalSpots,
        @Min(0) int occupiedSpots) {
}
