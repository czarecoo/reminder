package com.czareg.reminder.service;

import com.czareg.reminder.model.ConsumedEvent;
import com.czareg.reminder.model.Event;
import com.czareg.reminder.repository.ConsumedEventRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@Service
@AllArgsConstructor
public class ConsumedEventService {
    private final ConsumedEventRepository consumedEventRepository;

    public List<ConsumedEvent> getAll() {
        return consumedEventRepository.findAll();
    }

    public Event consume(Event event) {
        ConsumedEvent consumedEvent = ConsumedEvent.builder()
                .event(event)
                .build();
        consumedEventRepository.saveAndFlush(consumedEvent);
        log.info("Consumed event: {}", event);
        return event;
    }

    public void delete(long id) {
        ConsumedEvent consumedEvent = consumedEventRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
        delete(consumedEvent);
    }

    public void delete(ConsumedEvent consumedEvent) {
        consumedEventRepository.delete(consumedEvent);
        log.info("Deleted consumedEvent: {}", consumedEvent);
    }
}