package com.turubarov.dadataproxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DadataproxyApplication {

	public static void main(String[] args) {
		SpringApplication.run(DadataproxyApplication.class, args);
	}

}
