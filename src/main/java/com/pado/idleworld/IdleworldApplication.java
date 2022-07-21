package com.pado.idleworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@EnableJpaAuditing // BaseEntity 사용을 위함
@SpringBootApplication
public class IdleworldApplication {

	public static void main(String[] args) {
		SpringApplication.run(IdleworldApplication.class, args);
	}

	//패스워드 인코딩
	@Bean
	public BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	}



}
