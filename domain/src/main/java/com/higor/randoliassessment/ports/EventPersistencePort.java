package com.higor.randoliassessment.ports;

import com.higor.randoliassessment.model.EventModel;

import java.util.List;
import java.util.UUID;


public interface EventPersistencePort {

    EventModel getById(UUID id);

    EventModel save(EventModel eventModel);

    List<EventModel> saveMany(List<EventModel> eventModels);

    List<EventModel> getAll();

    EventModel update(EventModel eventModel);

    void delete(UUID eventId);

}
