package com.higor.randoliassessment.adapters;

import com.higor.randoliassessment.entities.Event;
import com.higor.randoliassessment.mapper.Mapper;
import com.higor.randoliassessment.model.EventModel;
import com.higor.randoliassessment.ports.EventPersistencePort;
import com.higor.randoliassessment.repositories.EventRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { EventPersistencePort.class, EventPersistenceAdapter.class })
class EventPersistenceAdapterTest {

    private final EventRepository eventRepository = Mockito.mock(EventRepository.class);

    private final Mapper mapper = Mapper.getInstance();

    private final EventPersistencePort eventPersistenceAdapter = new EventPersistenceAdapter(eventRepository, mapper);

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getById() {
        EventModel expectedModel = this.createEventModel();
        expectedModel.setEventId(UUID.randomUUID());
        Optional<Event> expectedEntity = Optional.of(mapper.convert(expectedModel));

        UUID id = expectedModel.getEventId();

        BDDMockito
            .when(this.eventRepository.findById(id))
            .thenReturn(expectedEntity);

        EventModel actualModel = this.eventPersistenceAdapter.getById(id);
        assertEquals(expectedModel, actualModel);
    }

    @Test
    void save() {
        EventModel modelToSave = this.createEventModel();
        Event expectedEntity = mapper.convert(modelToSave);
        expectedEntity.setEventId(UUID.randomUUID());

        BDDMockito
            .when(this.eventRepository.save(ArgumentMatchers.any(Event.class)))
            .thenReturn(expectedEntity);

        EventModel actualEventModel = this.eventPersistenceAdapter.save(modelToSave);

        assertEquals(expectedEntity.getEventId(), actualEventModel.getEventId());
    }

    @Test
    void getAll() {
        List<Event> expectedEvents = List.of(
            createEvent(),
            createEvent(),
            createEvent(),
            createEvent()
        );

        List<EventModel> expectedEventModels = expectedEvents
            .stream()
            .map(mapper::convert)
            .collect(Collectors.toList());

        BDDMockito
            .when(this.eventRepository.findAll())
            .thenReturn(expectedEvents);

        List<EventModel> actualEventModels = this.eventPersistenceAdapter.getAll();

        assertEquals(expectedEventModels, actualEventModels);
    }

    @Test
    void update() {
        EventModel modelToUpdate = createEventModel();
        Event event = mapper.convert(modelToUpdate);

        BDDMockito
            .when(this.eventRepository.save(ArgumentMatchers.any(Event.class)))
            .thenReturn(event);

        EventModel actualModel = this.eventPersistenceAdapter.update(modelToUpdate);

        assertEquals(modelToUpdate, actualModel);
    }

    @Test
    void delete() {
        UUID id = UUID.randomUUID();

        this.eventPersistenceAdapter.delete(id);
        BDDMockito.verify(this.eventRepository, times(1)).deleteById(id);
    }

    EventModel createEventModel() {
        return EventModel.builder()
            .transId(UUID.randomUUID())
            .transTms("Test")
            .rcNum("10002")
            .clientId("RPS-0001")
            .eventCnt(1)
            .locationCd("DESTINATION")
            .locationId1("T8C")
            .locationId2("1j7")
            .addrNbr("0000001")
            .build();
    }

    Event createEvent() {
        return Event.builder()
            .eventId(UUID.randomUUID())
            .transId(UUID.randomUUID())
            .transTms("Test")
            .rcNum("10002")
            .clientId("RPS-0001")
            .eventCnt(1)
            .locationCd("DESTINATION")
            .locationId1("T8C")
            .locationId2("1j7")
            .addrNbr("0000001")
            .build();
    }
}