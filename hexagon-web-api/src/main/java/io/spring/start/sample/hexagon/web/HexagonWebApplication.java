package io.spring.start.sample.hexagon.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
		scanBasePackages = {"io.spring.start.sample.hexagon"})
public class HexagonWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(HexagonWebApplication.class, args);
	}

}
