package com.example.adminstats.controller;

import java.time.LocalDate;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @GetMapping("/{summary_date}")
    public Summary getSummary(@PathVariable String summary_date){
        LocalDate date = LocalDate.parse(summary_date);
        return analyticsService.getSummary(date);
    }
}
