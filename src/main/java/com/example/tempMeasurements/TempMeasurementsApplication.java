package com.example.tempMeasurements;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class TempMeasurementsApplication {

	public static void main(String[] args) {
		SpringApplication.run(TempMeasurementsApplication.class, args);
	}

}
