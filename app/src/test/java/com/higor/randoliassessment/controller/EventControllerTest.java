package com.higor.randoliassessment.controller;

import com.higor.randoliassessment.entities.Event;
import com.higor.randoliassessment.model.BatchEventModel;
import com.higor.randoliassessment.model.EventModel;
import com.higor.randoliassessment.model.Record;
import com.higor.randoliassessment.repositories.EventRepository;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import org.apache.camel.CamelContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EventControllerTest {

    @Autowired
    private EventRepository repository;

    @Qualifier("createRestTemplate")
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    protected CamelContext camelContext;

    @LocalServerPort
    private int randomServerPort;

    private final String baseUrl = "http://localhost:";

    private List<Event> events;

    @BeforeEach
    void dataSeed() throws Exception {
        this.events = this.repository.saveAll(List.of(createEvent()));
        camelContext.start();
    }

    @AfterEach
    void flush() throws Exception {
        this.repository.flush();
        camelContext.stop();
    }

    @Test
    @Order(1)
    void getById() throws Exception {
        UUID id = events.get(0).getEventId();

        URI uri = new URI(baseUrl + randomServerPort + "/api/event/".concat(id.toString()));
        ResponseEntity<EventModel> response = restTemplate.getForEntity(uri, EventModel.class);
        Assertions.assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    @Order(2)
    void getById_WithNonExistentId() throws Exception {
        UUID id = UUID.randomUUID();

        URI uri = new URI(baseUrl + randomServerPort + "/api/event/".concat(id.toString()));
        ResponseEntity<EventModel> response = restTemplate.getForEntity(uri, EventModel.class);
        Assertions.assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    @Order(3)
    void getAllEvents() throws Exception {
        URI uri = new URI(baseUrl + randomServerPort + "/api/event");
        ResponseEntity<EventModel[]> response = restTemplate.getForEntity(uri, EventModel[].class);
        Assertions.assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    @Order(4)
    void save() throws Exception {
        EventModel eventModel = this.createEventModel();

        URI uri = new URI(baseUrl + randomServerPort + "/api/event");

        ResponseEntity<EventModel> response = restTemplate.exchange(
            String.valueOf(uri),
            HttpMethod.POST,
            new HttpEntity<>(eventModel),
            new ParameterizedTypeReference<>() {
            }
        );

        Assertions.assertEquals(200, response.getStatusCodeValue());
    }


    @Test
    @Order(5)
    void update() throws Exception {
        EventModel eventModel = this.createEventModel();
        eventModel.setEventId(events.get(0).getEventId());

        URI uri = new URI(baseUrl + randomServerPort + "/api/event");

        ResponseEntity<EventModel> response = restTemplate.exchange(
            String.valueOf(uri),
            HttpMethod.PUT,
            new HttpEntity<>(eventModel),
            new ParameterizedTypeReference<>() {
            }
        );

        Assertions.assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    @Order(6)
    void delete() throws Exception {
        UUID id = events.get(0).getEventId();

        URI uri = new URI(baseUrl + randomServerPort + "/api/event/".concat(id.toString()));

        ResponseEntity<EventModel> response = restTemplate.exchange(
            String.valueOf(uri),
            HttpMethod.DELETE,
            null,
            new ParameterizedTypeReference<>() {}
        );

        Assertions.assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    @Order(7)
    void deleteNonexistentId() throws Exception {
        UUID id = UUID.randomUUID();

        URI uri = new URI(baseUrl + randomServerPort + "/api/event/".concat(id.toString()));

        ResponseEntity<EventModel> response = restTemplate.exchange(
            String.valueOf(uri),
            HttpMethod.DELETE,
            null,
            new ParameterizedTypeReference<>() {}
        );

        Assertions.assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    @Order(8)
    void saveMany() throws Exception {
        BatchEventModel batchEventModel = createBatchEventModel();


        URI uri = new URI(baseUrl + randomServerPort + "/api/event/batch");

        ResponseEntity<List<EventModel>> response = restTemplate.exchange(
            String.valueOf(uri),
            HttpMethod.POST,
            new HttpEntity<>(batchEventModel),
            new ParameterizedTypeReference<>() {}
        );

        Assertions.assertEquals(200, response.getStatusCodeValue());
    }


    private BatchEventModel createBatchEventModel() {
        return BatchEventModel.builder()
            .batchId(UUID.randomUUID())
            .records(
                List.of(
                    createRecord(),
                    createRecord(),
                    createRecord(),
                    createRecord()
                )
            )
            .build();
    }
    private Record createRecord() {
        return Record.builder()
            .transId(UUID.randomUUID())
            .transTms("Test")
            .rcNum("10002")
            .clientId("RPS-0001")
            .event(
                List.of(
                    createEventModel(),
                    createEventModel(),
                    createEventModel(),
                    createEventModel()
                )
            ).build();
    }
    private Event createEvent() {
        return Event.builder()
            .transId(UUID.randomUUID())
            .transTms("Test")
            .rcNum("10002")
            .clientId("RPS-0001")
            .eventCnt(1)
            .locationCd("DESTINATION")
            .locationId1("T8C")
            .locationId2("1J7")
            .addrNbr("0000001")
            .build();
    }

    private EventModel createEventModel() {
        return EventModel.builder()
            .transId(UUID.randomUUID())
            .transTms("Test")
            .rcNum("10002")
            .clientId("RPS-0001")
            .eventCnt(1)
            .locationCd("DESTINATION")
            .locationId1("T8C")
            .locationId2("1J7")
            .addrNbr("0000001")
            .build();
    }
}