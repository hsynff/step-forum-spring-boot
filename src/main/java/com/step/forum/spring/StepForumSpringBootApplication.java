package com.step.forum.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class StepForumSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(StepForumSpringBootApplication.class, args);
	}

}

