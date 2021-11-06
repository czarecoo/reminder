package com.czareg.reminder.service;

import com.czareg.reminder.model.ConsumedEvent;
import com.czareg.reminder.model.Event;
import com.czareg.reminder.repository.ConsumedEventRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ConsumedEventService {
    private final ConsumedEventRepository consumedEventRepository;

    public List<ConsumedEvent> getAll() {
        return consumedEventRepository.findAll();
    }

    public void consume(Event event) {
        ConsumedEvent consumedEvent = new ConsumedEvent();
        consumedEvent.setEvent(event);
        consumedEventRepository.saveAndFlush(consumedEvent);
        log.info("Consumed event: {}", event);
    }
}