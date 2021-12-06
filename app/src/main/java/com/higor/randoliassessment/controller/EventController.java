package com.higor.randoliassessment.controller;

import com.higor.randoliassessment.model.BatchEventModel;
import com.higor.randoliassessment.model.EventModel;
import com.higor.randoliassessment.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(EventController.ENDPOINT)
public class EventController {

    public static final String ENDPOINT = "/api/event";

    @Autowired
    private EventService eventService;


    @GetMapping("/{id}")
    public ResponseEntity<EventModel> getById(@PathVariable UUID id) {
        EventModel eventModel = this.eventService.getById(id);
        return ResponseEntity.ok(eventModel);
    }

    @GetMapping
    public ResponseEntity<List<EventModel>> getAllEvents() {
        return ResponseEntity.ok(this.eventService.getAll());
    }

    @PostMapping
    public ResponseEntity<EventModel> save(@RequestBody EventModel eventModel) {
        EventModel createdEvent = this.eventService.save(eventModel);
        return ResponseEntity
            .created(URI.create(ENDPOINT.concat(createdEvent.getEventId().toString())))
            .body(createdEvent);
    }

    @PostMapping("/batch")
    public ResponseEntity<List<EventModel>> saveMany(@RequestBody BatchEventModel batchEventModel) {
        List<EventModel> createdEvents = this.eventService.saveMany(batchEventModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdEvents);
    }
    @PutMapping
    public ResponseEntity<EventModel> update(@RequestBody EventModel eventModel) {
        EventModel updatedEvent = this.eventService.update(eventModel);

        return ResponseEntity.ok(updatedEvent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EventModel> delete(@PathVariable UUID id) {
        this.eventService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
