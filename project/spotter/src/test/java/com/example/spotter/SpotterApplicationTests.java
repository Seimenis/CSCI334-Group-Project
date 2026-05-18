package com.example.spotter;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "spotter.kafka.enabled=false")
class SpotterApplicationTests {

	@Test
	void contextLoads() {
	}
}
