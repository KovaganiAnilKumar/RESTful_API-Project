package com.sathya.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import jakarta.persistence.Cacheable;

@SpringBootApplication
@EnableCaching
public class SpringBootRestProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootRestProjectApplication.class, args);
	}

}
