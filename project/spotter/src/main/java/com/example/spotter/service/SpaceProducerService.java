package com.example.spotter.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.spotter.dto.event.SpaceUpdatedEvent;
import com.example.spotter.service.dto.event.SpaceCreatedEvent;

@Service
public class SpaceProducerService {
	private final KafkaTemplate<String, Object> kafkaTemplate;
	
	public SpaceProducerService(KafkaTemplate<String, Object> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}
	
	public void publishSpaceCreatedEvent(SpaceCreatedEvent event) {
		kafkaTemplate.send("spotter.created", event);
	}
	
	public void publishSpaceUpdatedEvent(SpaceUpdatedEvent event) {
		kafkaTemplate.send("spotter.updated", event);
	}
}
