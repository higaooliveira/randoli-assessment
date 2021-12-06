package com.higor.randoliassessment.services;

import com.higor.randoliassessment.exceptions.InternalServerError;
import com.higor.randoliassessment.model.BatchEventModel;
import com.higor.randoliassessment.model.EventModel;
import com.higor.randoliassessment.model.Record;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;
import lombok.AllArgsConstructor;
import com.higor.randoliassessment.ports.EventPersistencePort;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.UUID;


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
    public EventModel getById(UUID id) {

        return this.eventPersistencePort.getById(id);
    }

    @Override
    public EventModel save(EventModel eventModel) {
        return this.eventPersistencePort.save(eventModel);
    }

    @Override
    public List<EventModel> saveMany(BatchEventModel batchEventModel) {
        AtomicReference<List<EventModel>> eventModels  = new AtomicReference<>(new ArrayList<>());

        Thread thread = new Thread(() -> {
            for (Record record : batchEventModel.getRecords()) {
                for (EventModel eventModel : record.getEvent()) {
                    eventModel.setTransId(record.getTransId());
                    eventModel.setTransTms(record.getTransTms());
                    eventModel.setRcNum(record.getRcNum());
                    eventModel.setClientId(record.getClientId());
                    eventModels.get().add(eventModel);
                }
            }
            eventModels.set(this.eventPersistencePort.saveMany(eventModels.get()));
        });

        thread.start();

        try{
            thread.join();
        }catch (InterruptedException ex) {
            throw new InternalServerError(ex.getLocalizedMessage());
        }

        return eventModels.get();
    }

    @Override
    public EventModel update(EventModel eventModel) {

        return this.eventPersistencePort.update(eventModel);
    }

    @Override
    public void delete(UUID id) {
        this.eventPersistencePort.delete(id);
    }
}
