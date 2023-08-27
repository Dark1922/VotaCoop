package com.br.VotaCoop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class VotaCoopApplication {

	public static void main(String[] args) {
		SpringApplication.run(VotaCoopApplication.class, args);
	}

}
