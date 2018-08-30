package com.going.storm.monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAutoConfiguration
@SpringBootApplication 
@EnableScheduling
public class WebApplication  {

	public static void main(String[] args) {
		System.setProperty("user.timezone", "Etc/GMT-8");
		SpringApplication.run(Application.class, args);
	}

}
