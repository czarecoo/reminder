package com.czareg.reminder.repository;

import com.czareg.reminder.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}