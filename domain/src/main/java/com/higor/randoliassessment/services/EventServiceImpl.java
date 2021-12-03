package com.higor.randoliassessment.services;

import com.higor.randoliassessment.model.EventModel;
import lombok.AllArgsConstructor;
import com.higor.randoliassessment.ports.EventPersistencePort;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;


@Named
@AllArgsConstructor
public class EventServiceImpl implements EventService {

    @Inject
    private final EventPersistencePort eventPersistencePort;

    @Override
    public List<EventModel> getAll() {
        return this.eventPersistencePort.getAll();
    }

    @Override
    public EventModel getById() {

        return null;
    }

    @Override
    public EventModel save(EventModel eventModel) {

        return this.eventPersistencePort.save(eventModel);
    }

    @Override
    public EventModel update() {

        return null;
    }

    @Override
    public void delete() {

    }
}
