package com.example.parking.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ParkingConsumer {

    @KafkaListener(topics = "parking-created", groupId = "parking-service-group")
    public void listen(String message) {
        System.out.println("Kafka message received: " + message);
    }
}