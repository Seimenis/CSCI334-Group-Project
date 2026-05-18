package com.example.spotter.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class SimulationRequest {

    @Min(1)
    @Max(250)
    private Integer eventCount = 1;

    private Boolean publishEvents = true;

    public Integer getEventCount() {
        return eventCount;
    }

    public void setEventCount(Integer eventCount) {
        this.eventCount = eventCount;
    }

    public Boolean getPublishEvents() {
        return publishEvents;
    }

    public void setPublishEvents(Boolean publishEvents) {
        this.publishEvents = publishEvents;
    }
}
