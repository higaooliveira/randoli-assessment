package com.higor.randoliassessment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages={"com.higor.randoliassessment"})
@SpringBootApplication
public class RandoliAssessmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(RandoliAssessmentApplication.class, args);
	}

}
