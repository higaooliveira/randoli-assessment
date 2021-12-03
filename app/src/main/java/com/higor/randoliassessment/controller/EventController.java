package com.higor.randoliassessment.controller;

import model.EventModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.EventService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EventController {

    @Autowired
    private EventService eventService;


    @GetMapping("/event")
    public ResponseEntity<List<EventModel>> getAllEvents() {
        return ResponseEntity.ok().body(this.eventService.getAll());
    }

    @PostMapping("/event")
    public ResponseEntity<EventModel> save(@RequestBody EventModel eventModel) {
        EventModel createdEvent = this.eventService.save(eventModel);
        return ResponseEntity.ok().body(createdEvent);
    }
}
