package adapters;

import entities.Event;
import lombok.AllArgsConstructor;
import mapper.EventMapper;
import model.EventModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;
import ports.EventPersistencePort;
import repositories.EventRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ComponentScan(basePackages={"repositories"})
@EnableJpaRepositories("repositories")
@EntityScan("entities")
@Component
@AllArgsConstructor
public class EventPersistenceAdapter implements EventPersistencePort {

    @Autowired
    @Qualifier("eventRepository")
    private final EventRepository eventRepository;

    @Qualifier("eventMapperImpl")
    @Autowired
    private final EventMapper mapper;

    @Override
    public EventModel save(EventModel eventModel) {
        Event event = mapper.toEntity(eventModel);
        return mapper.toModel(this.eventRepository.save(event));
    }

    @Override
    public List<EventModel> getAll() {
        return this.eventRepository
                .findAll()
                .stream()
                .map(this.mapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public EventModel update(EventModel eventModel) {
        return null;
    }

    @Override
    public void delete(UUID eventId) {

    }
}
