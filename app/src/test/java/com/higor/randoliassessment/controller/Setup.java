package com.higor.randoliassessment.controller;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Setup {
    @Bean
    public TestRestTemplate createRestTemplate() {
        return new TestRestTemplate();
    }

}
