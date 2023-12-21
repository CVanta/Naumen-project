package com.urfu.tgbot.repository;

import com.urfu.tgbot.model.Trip;
import org.springframework.data.repository.CrudRepository;

/**
 * Репозиторий Поездок
 */
public interface TripRepository extends CrudRepository<Trip, Long> {
}
