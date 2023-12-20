package com.urfu.tgbot.repository;

import com.urfu.tgbot.model.Trip;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий Поездок
 */
@Repository
public interface TripRepository extends CrudRepository<Trip, Long> {
}
