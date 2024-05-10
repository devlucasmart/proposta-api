package com.devlucasmart.propostaapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PropostaApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PropostaApiApplication.class, args);
	}

}
