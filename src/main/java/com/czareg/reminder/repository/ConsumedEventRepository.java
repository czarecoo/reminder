package com.czareg.reminder.repository;

import com.czareg.reminder.model.ConsumedEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsumedEventRepository extends JpaRepository<ConsumedEvent, Long> {
}