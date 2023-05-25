package com.case_study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Class Name: CaseStudy
 * 
 * The @EnableScheduling annotation is used to enable the scheduler for your
 * application. This annotation should be added into the main Spring Boot
 * application class file.
 * 
 * The @PropertySource annotation to externalize our configuration to a
 * properties file. In this way, we can read a properties file and display the
 * values with @Value and Environment.
 * 
 * @author saloni.sharma
 */
@SpringBootApplication
@EnableScheduling
@PropertySource(value = { "classpath:environment/application.properties" })
public class CaseStudy {
	public static void main(String[] args) {
		SpringApplication.run(CaseStudy.class, args);
	}
}
