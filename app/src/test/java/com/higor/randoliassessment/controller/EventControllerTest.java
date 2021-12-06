package com.higor.randoliassessment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.higor.randoliassessment.entities.Event;
import com.higor.randoliassessment.model.BatchEventModel;
import com.higor.randoliassessment.model.EventModel;
import com.higor.randoliassessment.model.Record;
import com.higor.randoliassessment.repositories.EventRepository;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EventControllerTest {

    @Autowired
    private EventRepository repository;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper jackson = Jackson2ObjectMapperBuilder.json().build();

    private static final String ENDPOINT = "/api/event";

    private List<Event> events;

    @BeforeEach
    void dataSeed() {
        this.events = this.repository.saveAll(List.of(createEvent()));
    }

    @AfterEach
    void flush() {
        this.repository.flush();
    }

    @Test
    @Order(1)
    void getById() throws Exception {
        UUID id = events.get(0).getEventId();
        String json = jackson.writeValueAsString(events.get(0));

        this.mockMvc.perform(
            MockMvcRequestBuilders
                .get(ENDPOINT.concat("/").concat(id.toString()))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(json));
    }

    @Test
    @Order(2)
    void getById_WithNonExistentId() throws Exception {
        UUID id = UUID.randomUUID();

        this.mockMvc.perform(
            MockMvcRequestBuilders
                .get(ENDPOINT.concat("/").concat(id.toString()))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @Order(3)
    void getAllEvents() throws Exception {
        this.mockMvc.perform(
            MockMvcRequestBuilders
                .get(ENDPOINT)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(4)
    void save() throws Exception {
        EventModel eventModel = this.createEventModel();
        this.mockMvc.perform(
            MockMvcRequestBuilders
                .post(ENDPOINT)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jackson.writeValueAsString(eventModel)))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }


    @Test
    @Order(5)
    void update() throws Exception {
        EventModel eventModel = this.createEventModel();
        eventModel.setEventId(events.get(0).getEventId());

        this.mockMvc.perform(
            MockMvcRequestBuilders
                .put(ENDPOINT)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jackson.writeValueAsString(eventModel)))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @Order(6)
    void delete() throws Exception {
        UUID id = events.get(0).getEventId();
        this.mockMvc.perform(
            MockMvcRequestBuilders
                .delete(ENDPOINT.concat("/").concat(id.toString()))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @Order(7)
    void deleteNonexistentId() throws Exception {
        UUID id = UUID.randomUUID();
        this.mockMvc.perform(
            MockMvcRequestBuilders
                .delete(ENDPOINT.concat("/").concat(id.toString()))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }


    @Test
    @Order(8)
    void saveMany() throws Exception {
        BatchEventModel batchEventModel = createBatchEventModel();
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .post(ENDPOINT.concat("/batch"))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jackson.writeValueAsString(batchEventModel)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
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

