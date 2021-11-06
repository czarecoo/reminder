package com.czareg.reminder.controller;

import com.czareg.reminder.model.ConsumedEvent;
import com.czareg.reminder.service.ConsumedEventService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/consumed-events")
@AllArgsConstructor
public class ConsumedEventController {
    private final ConsumedEventService consumedEventService;

    @GetMapping
    public List<ConsumedEvent> getAll() {
        return consumedEventService.getAll();
    }
}