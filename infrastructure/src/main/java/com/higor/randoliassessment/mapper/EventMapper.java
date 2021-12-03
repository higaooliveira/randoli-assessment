package com.higor.randoliassessment.mapper;

import com.higor.randoliassessment.entities.Event;
import com.higor.randoliassessment.model.EventModel;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface EventMapper {

    EventModel toModel(Event event);

    Event toEntity(EventModel eventModel);
}
