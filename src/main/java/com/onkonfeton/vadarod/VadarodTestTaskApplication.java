package com.onkonfeton.vadarod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class VadarodTestTaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(VadarodTestTaskApplication.class, args);
	}

}
