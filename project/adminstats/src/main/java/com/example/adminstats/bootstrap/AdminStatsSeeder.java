package com.example.adminstats.bootstrap;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.adminstats.model.Snapshot;
import com.example.adminstats.repository.AdminRepository;

import com.github.javafaker.Faker;

@Component
public class AdminStatsSeeder implements CommandLineRunner {

    private final AdminRepository adminRepository;

    private static final Faker faker = new Faker();
    private static final int NUMBER_OF_ENTRIES = 100;
    
    public AdminStatsSeeder(AdminRepository adminRepository){
        this.adminRepository = adminRepository;
    }

    // generates the comma-seperated occupancyPerHour variable for snapshotDTO
    // follows a multimodal distribution (3 gaussian combined) to simulate the activity of a real car park with busy times
    private String generateOccupancy(int totalSpots) {
        int[] occupancy = new int[24];
        Random random = new Random();

        for (int hour = 0; hour < 24; hour++) {
            double morning = Math.exp(-0.5 * Math.pow((hour - 9) / 2.0, 2));
            double lunch   = Math.exp(-0.5 * Math.pow((hour - 12) / 1.5, 2));
            double cob     = Math.exp(-0.5 * Math.pow((hour - 17) / 2.0, 2));

            double combined = (morning * 0.8 + lunch * 0.6 + cob * 0.9) / 2.3;
            int base = (int) (combined * totalSpots);

            int noise = (int) (random.nextGaussian() * totalSpots * 0.05);
            occupancy[hour] = Math.clamp(base + noise, 0, totalSpots);
        }

        return Arrays.stream(occupancy)
                 .mapToObj(Integer::toString)
                 .collect(Collectors.joining(","));
    }

    public void run(String... args){

        // Creates a auto-sorting list of dates that will igonore duplicates
        Set<LocalDate> dateSet = new TreeSet<>();

        while (dateSet.size() < NUMBER_OF_ENTRIES) {
            Date randomDate = faker.date().past(366, TimeUnit.DAYS);
            LocalDate date = randomDate.toInstant()
                                   .atZone(ZoneId.systemDefault())
                                   .toLocalDate();
            dateSet.add(date);
        }

        // Convert Set to List to loop
        List<LocalDate> dates = new ArrayList<>(dateSet);
        // int counter = 0;

        for (LocalDate date : dates) {
            Snapshot snapshot = new Snapshot();
            snapshot.setLotId(1);
            snapshot.setDate(date);
            snapshot.setOccupancy(generateOccupancy(250));
            snapshot.setSpotsTotal(250);
            adminRepository.save(snapshot);
            /* counter++;
            if(counter == (dates.size())-1){
                // most recent entry will have missing values for occupancy
                SnapshotDTO last_snapshot = new SnapshotDTO();
                snapshot.setLotId(1);
                String str_date = date.format(DateTimeFormatter.ofPattern("YYYY/MM/DD")); // LocalDate -> String (for storage)
                snapshot.setDate(str_date);
                snapshot.setOccupancy(null);
                snapshot.setSpotsTotal(500);
                adminRepository.save(snapshot);
            } */
        }
    }
}
