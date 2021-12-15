package com.higor.randoliassessment.services;

import com.higor.randoliassessment.exceptions.InternalServerError;
import com.higor.randoliassessment.exceptions.ResourceNotFound;
import com.higor.randoliassessment.model.BatchEventModel;
import com.higor.randoliassessment.model.EventModel;

import java.util.List;
import java.util.UUID;


public interface EventService {

    List<EventModel> getAll();

    EventModel getById(UUID id) throws ResourceNotFound;

    EventModel save(EventModel eventmodel);

    List<EventModel> saveMany(BatchEventModel batchEventModel) throws InternalServerError;

    EventModel update(EventModel eventModel);

    void delete(UUID id) throws ResourceNotFound;
}
