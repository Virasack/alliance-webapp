package com.webapp.alliance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AllianceWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(AllianceWebApplication.class, args);
	}
}
