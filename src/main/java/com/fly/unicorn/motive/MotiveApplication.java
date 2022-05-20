package com.fly.unicorn.motive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MotiveApplication {
	public static void main(String[] args) {
		SpringApplication.run(MotiveApplication.class, args);
	}
}
