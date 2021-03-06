package com.higor.randoliassessment.repositories;

import com.higor.randoliassessment.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@Component
public interface EventRepository extends JpaRepository<Event, UUID> {

}
