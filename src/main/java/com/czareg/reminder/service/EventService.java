package com.czareg.reminder.service;

import com.czareg.reminder.model.ConsumedEvent;
import com.czareg.reminder.model.Event;
import com.czareg.reminder.repository.EventRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@Service
@AllArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final ConsumedEventService consumedEventService;

    public List<Event> getAll() {
        return eventRepository.findAll();
    }

    public Event get(long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }

    public Event createOrUpdate(Event event) {
        return eventRepository.saveAndFlush(event);
    }

    public void delete(long id) {
        Event event = get(id);
        event.getConsumedEvents()
                .forEach(consumedEventService::delete);
        eventRepository.deleteById(id);
        log.info("Deleted event: {}", event);
    }

    public List<Event> getConsumable() {
        return getAll()
                .stream()
                .filter(isToday())
                .filter(wasNotAlreadyConsumedToday())
                .collect(toList());
    }

    public Event consume(long id) {
        Event eventFromDb = get(id);
        return consumedEventService.consume(eventFromDb);
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