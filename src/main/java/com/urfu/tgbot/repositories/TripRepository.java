package com.urfu.tgbot.repositories;

import com.urfu.tgbot.models.Trip;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripRepository extends CrudRepository<Trip, Long> {
    //List<Trip> findAllBy(Trip trip);

}
