package com.higor.randoliassessment.mapper;

import com.higor.randoliassessment.entities.Event;
import com.higor.randoliassessment.model.EventModel;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class Mapper extends ModelMapper {

    private static Mapper INSTANCE = null;

    private Mapper() {
        this.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        this.getConfiguration().setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);
        this.getConfiguration().setFieldMatchingEnabled(true);
        this.getConfiguration().setSkipNullEnabled(true);
    }

    public EventModel convert(Event source) {
        return this.map(source, EventModel.class);
    }

    public Event convert(EventModel source) {
        return this.map(source, Event.class);
    }

    public static Mapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Mapper();
        }
        return INSTANCE;
    }

}
