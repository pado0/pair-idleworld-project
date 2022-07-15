package com.pado.idleworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // BaseEntity 사용을 위함
@SpringBootApplication
public class IdleworldApplication {

	public static void main(String[] args) {
		SpringApplication.run(IdleworldApplication.class, args);
	}

}
