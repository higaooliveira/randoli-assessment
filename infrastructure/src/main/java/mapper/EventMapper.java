package mapper;

import entities.Event;
import model.EventModel;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface EventMapper {

    EventModel toModel(Event event);

    Event toEntity(EventModel eventModel);
}
