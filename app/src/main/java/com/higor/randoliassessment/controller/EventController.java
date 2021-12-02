package com.higor.randoliassessment.controller;

import exceptions.ResourceNotFound;
import model.EventModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.EventService;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class EventController {

    @Autowired
    private EventService eventService;


    @GetMapping("/event")
    public ResponseEntity<String> getAllEvents() {
        throw new ResourceNotFound(UUID.randomUUID());
        //return ResponseEntity.ok().body(this.eventService.getAll());
    }

    @PostMapping("/event")
    public ResponseEntity<EventModel> save(@RequestBody EventModel eventModel) {
        return ResponseEntity.ok().body(eventModel);
    }
}
