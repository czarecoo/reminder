package com.czareg.reminder.controller;

import com.czareg.reminder.model.Event;
import com.czareg.reminder.service.EventService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
@AllArgsConstructor
public class EventController {
    private final EventService eventService;

    @GetMapping
    public List<Event> getAll() {
        return eventService.getAll();
    }

    @GetMapping("/{id}")
    public Event get(@PathVariable long id) {
        return eventService.get(id);
    }

    @PostMapping
    public Event createOrUpdate(@RequestBody Event event) {
        return eventService.createOrUpdate(event);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        eventService.delete(id);
    }

    @GetMapping("/consumable")
    public List<Event> getConsumable() {
        return eventService.getConsumable();
    }

    @PostMapping("/consume/{id}")
    public Event consume(@PathVariable long id) {
        return eventService.consume(id);
    }
}