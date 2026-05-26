package com.example.occupancy.model;

public record ParkingLot(String lotId, String name, int totalCapacity, ParkingSpace[] spaces) {
}
