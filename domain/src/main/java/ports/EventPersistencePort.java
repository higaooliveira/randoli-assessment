package ports;

import model.EventModel;

import java.util.List;
import java.util.UUID;


public interface EventPersistencePort {

    EventModel save(EventModel eventModel);

    List<EventModel> getAll();

    EventModel update(EventModel eventModel);

    void delete(UUID eventId);

}
