package com.higor.randoliassessment.services;

import com.higor.randoliassessment.model.BatchEventModel;
import com.higor.randoliassessment.model.EventModel;

import java.util.List;
import java.util.UUID;


public interface EventService {

    List<EventModel> getAll();

    EventModel getById(UUID id);

    EventModel save(EventModel eventmodel);

    List<EventModel> saveMany(BatchEventModel batchEventModel);

    EventModel update(EventModel eventModel);

    void delete(UUID id);
}
