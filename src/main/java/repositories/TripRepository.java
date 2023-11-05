package repositories;

import com.example.TG_BOT.models.Trip;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripRepository extends CrudRepository<Trip, Long> {
    List<Trip> findAllBy(Trip trip);

}
