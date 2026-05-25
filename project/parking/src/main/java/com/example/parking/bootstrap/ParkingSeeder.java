package com.example.parking.bootstrap;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.parking.model.Parking;
import com.example.parking.repository.ParkingRepository;

@Component
public class ParkingSeeder implements CommandLineRunner {

    private final ParkingRepository parkingRepository;
    private static final Random random = new Random();
    private static final int NUMBER_OF_ENTRIES = 100;

    public ParkingSeeder(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }

    @Override
    public void run(String... args) {

        for (int i = 0; i < NUMBER_OF_ENTRIES; i++) {

            Parking parking = new Parking();

            parking.setAccountId((long) (i + 1));
            parking.setParkingLot("UOW Lot " + (random.nextInt(5) + 1));
            parking.setParkingSpace("A" + (i + 1));
            parking.setVehicle("Vehicle " + (i + 1));

            LocalDateTime startTime = LocalDateTime.now().plusHours(random.nextInt(72));

            parking.setStartTime(startTime);
            parking.setEndTime(startTime.plusHours(random.nextInt(5) + 1));
            parking.setCost(5.0 + random.nextInt(30));
            parking.setStatus(random.nextBoolean() ? "ACTIVE" : "COMPLETED");
            parking.setCreatedAt(LocalDateTime.now());

            parkingRepository.save(parking);
        }
    }
}