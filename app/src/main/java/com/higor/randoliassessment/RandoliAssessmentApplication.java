package com.higor.randoliassessment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages={"services", "model", "ports", "adapters", "mapper", "repositories"})
@SpringBootApplication
public class RandoliAssessmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(RandoliAssessmentApplication.class, args);
	}

}
