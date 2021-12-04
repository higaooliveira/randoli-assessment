package com.higor.randoliassessment.mapper;

import com.higor.randoliassessment.entities.Event;
import com.higor.randoliassessment.model.EventModel;
import java.util.UUID;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapperTest {


    @Test
    void convertEventModelToEvent() {
        Mapper mapper = Mapper.getInstance();
        EventModel eventModel= createEventModel();
        Event event = mapper.convert(eventModel);

        assertEquals(event.getEventId(), eventModel.getEventId());
    }

    @Test
    void convertEventToEventModel() {
        Mapper mapper = Mapper.getInstance();
        Event event = createEvent();
        EventModel eventModel = mapper.convert(event);

        assertEquals(eventModel.getEventId(), event.getEventId());
    }

    @Test
    void getInstance() {
        Mapper mapper = Mapper.getInstance();
        Mapper anotherMapper = Mapper.getInstance();

        assertEquals(mapper, anotherMapper);
    }

    EventModel createEventModel() {
        return EventModel.builder()
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