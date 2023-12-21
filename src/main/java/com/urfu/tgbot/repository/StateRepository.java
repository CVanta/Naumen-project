package com.urfu.tgbot.repository;

import com.urfu.tgbot.model.State;
import org.springframework.data.repository.CrudRepository;

/**
 * Репозиторий состояний
 */
public interface StateRepository extends CrudRepository<State, Long> {
}
