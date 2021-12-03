package services;

import model.EventModel;

import java.util.List;

public interface EventService {

    List<EventModel> getAll();

    EventModel getById();

    EventModel save(EventModel eventmodel);

    EventModel update();

    void delete();
}
