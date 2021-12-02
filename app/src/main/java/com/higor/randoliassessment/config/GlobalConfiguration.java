package com.higor.randoliassessment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import services.EventService;
import services.EventServiceImpl;

@Configuration
public class GlobalConfiguration {

    @Bean
    public EventService getEventService() {
        return new EventServiceImpl();
    }
}
