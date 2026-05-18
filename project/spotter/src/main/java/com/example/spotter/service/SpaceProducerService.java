package com.example.spotter.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.spotter.dto.event.SpaceUpdatedEvent;
import com.example.spotter.service.dto.event.SpaceCreatedEvent;

@Service
public class SpaceProducerService {
	private static final Logger log = LoggerFactory.getLogger(SpaceProducerService.class);

	private final KafkaTemplate<String, Object> kafkaTemplate;
	private final boolean kafkaEnabled;
	
	public SpaceProducerService(
			KafkaTemplate<String, Object> kafkaTemplate,
			@Value("${spotter.kafka.enabled:true}") boolean kafkaEnabled) {
		this.kafkaTemplate = kafkaTemplate;
		this.kafkaEnabled = kafkaEnabled;
	}
	
	public void publishSpaceCreatedEvent(SpaceCreatedEvent event) {
		publish("spotter.created", String.valueOf(event.getId()), event);
	}
	
	public void publishSpaceUpdatedEvent(SpaceUpdatedEvent event) {
		publish("spotter.updated", String.valueOf(event.getId()), event);
	}

	private void publish(String topic, String key, Object event) {
		if (!kafkaEnabled) {
			return;
		}
		try {
			kafkaTemplate.send(topic, key, event).whenComplete((result, exception) -> {
				if (exception != null) {
					log.warn("Could not publish {} event for key {}", topic, key, exception);
				}
			});
		} catch (RuntimeException exception) {
			log.warn("Could not queue {} event for key {}", topic, key, exception);
		}
	}
}
