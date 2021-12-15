package com.higor.randoliassessment.services;

import com.higor.randoliassessment.exceptions.ResourceNotFound;
import com.higor.randoliassessment.model.EventModel;
import com.higor.randoliassessment.ports.EventPersistencePort;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

@SpringBootTest
@ContextConfiguration(classes = EventServiceImpl.class)
class EventServiceImplTest {

    @MockBean
    private EventPersistencePort eventPersistencePort;

    @Autowired
    private EventService eventService;

    List<EventModel> expectedEventModels = List.of(
        createEventModel(
            UUID.randomUUID(),
            UUID.randomUUID(),
            "Test",
            "10002",
            "RPS-0001",
            1,
            "DESTINATION",
            "T8C",
            "1J7",
            "0000001"
        ),
        createEventModel(
            UUID.randomUUID(),
            UUID.randomUUID(),
            "Test",
            "10002",
            "RPS-0001",
            1,
            "DESTINATION",
            "T8C",
            "1J7",
            "0000001"
        ),
        createEventModel(
            UUID.randomUUID(),
            UUID.randomUUID(),
            "Test",
            "10002",
            "RPS-0001",
            1,
            "DESTINATION",
            "T8C",
            "1J7",
            "0000001"
        ),
        createEventModel(
            UUID.randomUUID(),
            UUID.randomUUID(),
            "Test",
            "10002",
            "RPS-0001",
            1,
            "DESTINATION",
            "T8C",
            "1J7",
            "0000001"
        )
    );


    @Test
    void getAll() {
        BDDMockito
            .when(this.eventPersistencePort.getAll())
            .thenReturn(expectedEventModels);

        List<EventModel> actualEventModels = this.eventService.getAll();

        assertEquals(expectedEventModels, actualEventModels);

    }

    @Test
    void getAllEmptyDatabase() {
        BDDMockito
            .when(this.eventPersistencePort.getAll())
            .thenReturn(List.of());

        List<EventModel> actualEventModels = this.eventService.getAll();

        assertTrue(actualEventModels.isEmpty());
    }

    @Test
    void getById() throws ResourceNotFound {
        UUID id = UUID.randomUUID();
        EventModel expectedModel = this.createEventModel(
            id,
            UUID.randomUUID(),
            "Test",
            "10002",
            "RPS-0001",
            1,
            "DESTINATION",
            "T8C",
            "1J7",
            "0000001"
        );

        BDDMockito
            .when(this.eventPersistencePort.getById(id))
            .thenReturn(expectedModel);

        EventModel actualModel = this.eventService.getById(id);
        assertEquals(expectedModel, actualModel);
    }

    @Test
    void getByNonexistentId() throws ResourceNotFound {
        UUID id = UUID.randomUUID();

        BDDMockito
            .when(this.eventPersistencePort.getById(id))
            .thenThrow(ResourceNotFound.class);

        assertThrows(ResourceNotFound.class, () -> this.eventService.getById(id));
    }

    @Test
    void save() {
        EventModel modelToSave = createEventModel(
            null,
            UUID.randomUUID(),
            "Test",
            "10002",
            "RPS-0001",
            1,
            "DESTINATION",
            "T8C",
            "1J7",
            "0000001"
        );

        EventModel expectedSavedModel = createEventModel(
            UUID.randomUUID(),
            modelToSave.getTransId(),
            modelToSave.getTransTms(),
            modelToSave.getRcNum(),
            modelToSave.getClientId(),
            modelToSave.getEventCnt(),
            modelToSave.getLocationCd(),
            modelToSave.getLocationId1(),
            modelToSave.getLocationId2(),
            modelToSave.getAddrNbr()
        );


        BDDMockito
            .when(this.eventPersistencePort.save(ArgumentMatchers.any(EventModel.class)))
            .thenReturn(expectedSavedModel);

        EventModel actualModel = this.eventService.save(modelToSave);

        assertEquals(expectedSavedModel, actualModel);
    }

    @Test
    void update() {
        EventModel modelToUpdate = createEventModel(
            UUID.randomUUID(),
            UUID.randomUUID(),
            "Test",
            "10002",
            "RPS-0001",
            1,
            "DESTINATION",
            "T8C",
            "1J7",
            "0000001"
        );

        BDDMockito
            .when(this.eventPersistencePort.update(ArgumentMatchers.any(EventModel.class)))
            .thenReturn(modelToUpdate);

        EventModel actualModel = this.eventService.update(modelToUpdate);

        assertEquals(modelToUpdate, actualModel);
    }

    @Test
    void delete() throws ResourceNotFound {
        UUID id = UUID.randomUUID();

        this.eventService.delete(id);
        BDDMockito.verify(this.eventPersistencePort, times(1)).delete(id);
    }

    private EventModel createEventModel(
        UUID eventId,
        UUID transId,
        String transTms,
        String rcNum,
        String clientId,
        int eventCnt,
        String locationCd,
        String locationId1,
        String locationId2,
        String addrNbr
    ) {
        return EventModel.builder()
            .eventId(eventId)
            .transId(transId)
            .transTms(transTms)
            .rcNum(rcNum)
            .clientId(clientId)
            .eventCnt(eventCnt)
            .locationCd(locationCd)
            .locationId1(locationId1)
            .locationId2(locationId2)
            .addrNbr(addrNbr)
            .build();
    }
}