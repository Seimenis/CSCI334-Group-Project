package com.example.parking.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.parking.model.Parking;
import com.example.parking.repository.ParkingRepository;

@Service
public class ParkingService {

    private final ParkingRepository parkingRepository;

    public ParkingService(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }

    public Parking createParking(Parking parking) {
        return parkingRepository.save(parking);
    }

    public List<Parking> getAllParking() {
        return parkingRepository.findAll();
    }

    public Parking getParkingById(Long id) {
        return parkingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Parking record not found"));
    }

    public Parking updateParking(Long id, Parking updatedParking) {
        Parking parking = getParkingById(id);

        if (updatedParking.getAccountId() != null) {
            parking.setAccountId(updatedParking.getAccountId());
        }

        if (updatedParking.getParkingLot() != null) {
            parking.setParkingLot(updatedParking.getParkingLot());
        }

        if (updatedParking.getParkingSpace() != null) {
            parking.setParkingSpace(updatedParking.getParkingSpace());
        }

        if (updatedParking.getVehicle() != null) {
            parking.setVehicle(updatedParking.getVehicle());
        }

        if (updatedParking.getStartTime() != null) {
            parking.setStartTime(updatedParking.getStartTime());
        }

        if (updatedParking.getEndTime() != null) {
            parking.setEndTime(updatedParking.getEndTime());
        }

        if (updatedParking.getCost() != null) {
            parking.setCost(updatedParking.getCost());
        }

        if (updatedParking.getStatus() != null) {
            parking.setStatus(updatedParking.getStatus());
        }

        return parkingRepository.save(parking);
    }

    public void deleteParking(Long id) {
        Parking parking = getParkingById(id);
        parkingRepository.delete(parking);
    }
}