package com.example.parking.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ParkingProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public ParkingProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendParkingCreatedEvent(String message) {
        kafkaTemplate.send("parking-created", message);
        System.out.println("Kafka message sent: " + message);
    }
}