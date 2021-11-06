package com.czareg.reminder.service;

import com.czareg.reminder.model.ConsumedEvent;
import com.czareg.reminder.model.Event;
import com.czareg.reminder.repository.EventRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final ConsumedEventService consumedEventService;

    public List<Event> getAll() {
        return eventRepository.findAll();
    }

    public Event createOrUpdate(Event event) {
        return eventRepository.saveAndFlush(event);
    }

    public void delete(Long id) {
        eventRepository.deleteById(id);
    }

    public List<Event> getConsumable() {
        return getAll()
                .stream()
                .filter(isToday())
                .filter(wasNotAlreadyConsumedToday())
                .peek(consumedEventService::consume)
                .collect(toList());
    }

    private Predicate<Event> isToday() {
        return event -> LocalDate.now().equals(event.getDate());
    }

    private Predicate<Event> wasNotAlreadyConsumedToday() {
        return event -> event.getConsumedEvents()
                .stream()
                .map(ConsumedEvent::getConsumptionDate)
                .noneMatch(consumptionDate -> consumptionDate.toLocalDate().equals(event.getDate()));
    }
}