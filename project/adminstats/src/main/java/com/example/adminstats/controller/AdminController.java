package com.example.adminstats.controller;

import java.time.LocalDate;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.adminstats.service.AnalyticsService;
import com.example.adminstats.model.Summary;

@RestController
@RequestMapping("adminstats")
public class AdminController {
    
    private final AnalyticsService analyticsService;

    public AdminController(AnalyticsService analyticsService){
        this.analyticsService = analyticsService;
    }
    // returns all admin statistical information for specified date
    @GetMapping(value = "/{summary_date}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> getSummary(@RequestParam LocalDate date){
        try {
            Summary summary = analyticsService.getSummary(date);
            return ResponseEntity.ok(summary.toString());
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
    // returns all admin statistical information for latest date available
    @GetMapping(value = "/latest", produces = MediaType.TEXT_PLAIN_VALUE)
    public String getLatest(){
        Summary summary = analyticsService.getLatest();
        return summary.toString();
    }
    
}
