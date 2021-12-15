package com.higor.randoliassessment.adapters;

import com.higor.randoliassessment.entities.Event;
import com.higor.randoliassessment.exceptions.ResourceNotFound;
import com.higor.randoliassessment.mapper.Mapper;
import lombok.AllArgsConstructor;
import com.higor.randoliassessment.model.EventModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import com.higor.randoliassessment.ports.EventPersistencePort;
import com.higor.randoliassessment.repositories.EventRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ComponentScan(basePackages={"com.higor.randoliassessment"})
@Component
@AllArgsConstructor
public class EventPersistenceAdapter implements EventPersistencePort {

    @Autowired
    @Qualifier("eventRepository")
    private final EventRepository eventRepository;

    @Autowired
    @Qualifier("mapper")
    private final Mapper mapper;

    @Override
    public EventModel getById(UUID id) throws ResourceNotFound {
        return this.eventRepository
            .findById(id)
            .map(mapper::convert)
            .orElseThrow(() -> new ResourceNotFound(id));
    }


    @Override
    public EventModel save(EventModel eventModel) {
        Event event = this.mapper.convert(eventModel);
        return this.mapper.convert(this.eventRepository.save(event));
    }

    @Override
    public List<EventModel> saveMany(List<EventModel> eventModels) {
        List<Event> events = eventModels.stream().map(mapper::convert).collect(Collectors.toList());

        return this.eventRepository
            .saveAll(events)
            .stream()
            .map(mapper::convert)
            .collect(Collectors.toList());
    }

    @Override
    public List<EventModel> getAll() {
        return this.eventRepository
            .findAll()
            .stream()
            .map(this.mapper::convert)
            .collect(Collectors.toList());
    }

    @Override
    public EventModel update(EventModel eventModel) {
        Event event = mapper.convert(eventModel);

        return mapper.convert(this.eventRepository.save(event));
    }

    @Override
    public void delete(UUID eventId) throws ResourceNotFound {
        try {
            this.eventRepository.deleteById(eventId);
        }catch (EmptyResultDataAccessException ex) {
            throw new ResourceNotFound(eventId);
        }
    }

}
