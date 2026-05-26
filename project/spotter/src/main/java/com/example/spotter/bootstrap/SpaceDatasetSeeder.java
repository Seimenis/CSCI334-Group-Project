package com.example.spotter.bootstrap;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.example.spotter.repository.SpaceRepository;
import com.example.spotter.service.SpotterDatasetService;

@Component
public class SpaceDatasetSeeder implements ApplicationRunner {

    private final SpaceRepository spaceRepository;
    private final SpotterDatasetService datasetService;

    public SpaceDatasetSeeder(SpaceRepository spaceRepository, SpotterDatasetService datasetService) {
        this.spaceRepository = spaceRepository;
        this.datasetService = datasetService;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (spaceRepository.count() == 0) {
            spaceRepository.saveAll(datasetService.loadSpaces());
        }
    }
}
