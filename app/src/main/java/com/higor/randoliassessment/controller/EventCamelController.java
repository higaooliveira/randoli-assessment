package com.higor.randoliassessment.controller;

import com.higor.randoliassessment.model.BatchEventModel;
import com.higor.randoliassessment.model.EventModel;
import com.higor.randoliassessment.services.EventService;
import java.util.UUID;
import org.apache.camel.Exchange;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class EventCamelController extends HandledRouter {

    private static final String ENDPOINT = "/api/event";

    @Autowired
    private EventService eventService;

    @Override
    public void configure() throws Exception {
        restConfiguration()
            .component("servlet")
            .host("localhost")
            .port(8080)
            .bindingMode(RestBindingMode.json);

        rest(ENDPOINT)
            .produces(MediaType.APPLICATION_JSON_VALUE)
            .get()
                .route()
                .routeId("get-all-events")
                .setBody(() -> eventService.getAll())
                .endRest()
            .get("/{id}")
                .route()
                .routeId("get-event-by-id")
                .process((Exchange request)->
                    request.getOut().setBody(
                        eventService.getById(
                            UUID.fromString(
                                request
                                    .getIn()
                                    .getHeader("id")
                                    .toString()
                            )
                        )
                    )
                )
                .endRest()
            .post()
                .consumes(MediaType.APPLICATION_JSON_VALUE)
                .type(EventModel.class)
                .outType(EventModel.class)
                .route()
                .routeId("create-event")
                /**
                 * Create a processor here ?
                 * **/
                .process((Exchange request) ->
                    request.getOut().setBody(eventService.save(request.getIn().getBody(EventModel.class)))
                )
                .endRest()
            .post("/batch")
                .consumes(MediaType.APPLICATION_JSON_VALUE)
                .type(BatchEventModel.class)
                .outType(EventModel[].class)
                .route()
                .routeId("create-batch-events")
                /**
                 * Create a processor here ?
                 * **/
                .process((Exchange request) ->
                    request.getOut().setBody(eventService.saveMany(request.getIn().getBody(BatchEventModel.class)))
                )
                .endRest()
            .put()
                .consumes(MediaType.APPLICATION_JSON_VALUE)
                .type(EventModel.class)
                .outType(EventModel.class)
                .route()
                .routeId("update-event")
                /**
                 * Create a processor here ?
                 * **/
                .process((Exchange request) -> eventService.update(request.getIn().getBody(EventModel.class)))
                .endRest()

            .delete("/{id}")
                .route()
                .routeId("delete-event")
                .process((Exchange request) ->
                    eventService.delete(UUID.fromString(request.getIn().getHeader("id").toString()))
                )
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(204))
                .setBody(() -> null)
                .endRest();

    }

}
