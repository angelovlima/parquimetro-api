package com.fiap.parquimetro.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ParquimetroApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParquimetroApiApplication.class, args);
	}

}
