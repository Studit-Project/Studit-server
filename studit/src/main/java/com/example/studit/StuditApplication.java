package com.example.studit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class StuditApplication {

	public static final String APPLICATION_LOCATIONS = "spring.config.location="
			+ "classpath:application.yml,"
			+ "classpath:aws.yml";
	public static void main(String[] args) {

		new SpringApplicationBuilder(StuditApplication.class)
				.properties(APPLICATION_LOCATIONS)
				.run(args);

//		SpringApplication.run(StuditApplication.class, args);


	}

}
