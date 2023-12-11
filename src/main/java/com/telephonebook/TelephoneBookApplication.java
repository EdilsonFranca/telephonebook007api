package com.telephonebook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TelephoneBookApplication {
	public static void main(String[] args) {
		SpringApplication.run(TelephoneBookApplication.class, args);
	}
}
