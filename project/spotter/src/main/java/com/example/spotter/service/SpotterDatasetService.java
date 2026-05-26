package com.example.spotter.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.example.spotter.model.SimulationFeedRecord;
import com.example.spotter.model.Space;

@Service
public class SpotterDatasetService {

    @Value("${spotter.dataset.spaces}")
    private Resource spacesDataset;

    @Value("${spotter.dataset.feed}")
    private Resource feedDataset;

    public List<Space> loadSpaces() {
        List<Space> spaces = new ArrayList<>();
        for (String[] row : readCsvRows(spacesDataset, 12)) {
            spaces.add(new Space(
                    row[1],
                    row[2],
                    row[3],
                    row[4],
                    Integer.parseInt(row[5]),
                    Boolean.parseBoolean(row[6]),
                    Boolean.parseBoolean(row[7]),
                    Double.parseDouble(row[8]),
                    row[9],
                    Instant.parse(row[10]),
                    Double.parseDouble(row[11]),
                    Double.parseDouble(row[12])));
        }
        return spaces;
    }

    public List<SimulationFeedRecord> loadSimulationFeed() {
        List<SimulationFeedRecord> feed = new ArrayList<>();
        for (String[] row : readCsvRows(feedDataset, 5)) {
            feed.add(new SimulationFeedRecord(
                    Long.parseLong(row[0]),
                    row[1],
                    Boolean.parseBoolean(row[2]),
                    Double.parseDouble(row[3]),
                    row[4],
                    Instant.parse(row[5])));
        }
        return feed;
    }

    private List<String[]> readCsvRows(Resource resource, int expectedLastIndex) {
        List<String[]> rows = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                if (line.isBlank() || line.startsWith("#")) {
                    continue;
                }
                String[] columns = line.split(",", -1);
                if (columns.length <= expectedLastIndex) {
                    throw new IllegalStateException("Invalid dataset row: " + line);
                }
                rows.add(columns);
            }
        } catch (IOException exception) {
            throw new IllegalStateException("Could not read dataset " + resource.getFilename(), exception);
        }
        return rows;
    }
}
