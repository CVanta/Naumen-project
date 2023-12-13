package com.urfu.tgbot.repositories;

import com.urfu.tgbot.models.State;
import org.springframework.data.repository.CrudRepository;

public interface StatesRepository extends CrudRepository<State, Long> {
}
