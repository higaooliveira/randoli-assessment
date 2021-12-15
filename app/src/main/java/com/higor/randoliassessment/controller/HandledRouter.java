package com.higor.randoliassessment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.higor.randoliassessment.exceptions.CustomException;
import com.higor.randoliassessment.model.StandardError;
import java.time.Instant;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public abstract class HandledRouter extends RouteBuilder {

    @Autowired
    private ObjectMapper mapper;


    public HandledRouter() {
        super();
        onException(CustomException.class)
            .handled(true)
            .process(exchange -> {
                CustomException cause = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, CustomException.class);

                exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, cause.getStatusCode());
                StandardError error = StandardError.builder()
                    .message(cause.getLocalizedMessage())
                    .timestamp(Instant.now())
                    .build();

                exchange.getOut().setHeader(Exchange.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                exchange.getOut().setBody(mapper.writeValueAsString(error));
            });
    }
}
