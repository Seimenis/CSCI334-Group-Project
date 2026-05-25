package com.example.parking.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.parking.dto.request.CreateParkingRequest;
import com.example.parking.kafka.ParkingProducer;
import com.example.parking.model.Parking;
import com.example.parking.service.ParkingService;

@RestController
@RequestMapping("/parking")
public class ParkingController {

    private final ParkingService parkingService;
    private final ParkingProducer parkingProducer;

    public ParkingController(
            ParkingService parkingService,
            ParkingProducer parkingProducer) {

        this.parkingService = parkingService;
        this.parkingProducer = parkingProducer;
    }

    @PostMapping
    public ResponseEntity<Parking> createParking(
            @RequestBody CreateParkingRequest request) {

        Parking parking = new Parking();

        parking.setAccountId(request.getAccountId());
        parking.setParkingLot(request.getParkingLot());
        parking.setParkingSpace(request.getParkingSpace());
        parking.setVehicle(request.getVehicle());
        parking.setStartTime(request.getStartTime());
        parking.setEndTime(request.getEndTime());
        parking.setCost(request.getCost());
        parking.setStatus(request.getStatus());

        Parking createdParking = parkingService.createParking(parking);

        parkingProducer.sendParkingCreatedEvent(
                "Parking created with ID: " + createdParking.getId()
        );

        return new ResponseEntity<>(createdParking, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Parking>> getAllParking() {
        return new ResponseEntity<>(
                parkingService.getAllParking(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Parking> getParkingById(
            @PathVariable Long id) {

        return new ResponseEntity<>(
                parkingService.getParkingById(id),
                HttpStatus.OK
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Parking> updateParking(
            @PathVariable Long id,
            @RequestBody Parking parking) {

        return new ResponseEntity<>(
                parkingService.updateParking(id, parking),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParking(
            @PathVariable Long id) {

        parkingService.deleteParking(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}