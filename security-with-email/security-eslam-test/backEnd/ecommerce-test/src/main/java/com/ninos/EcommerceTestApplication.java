package com.ninos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class EcommerceTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceTestApplication.class, args);
	}

}
