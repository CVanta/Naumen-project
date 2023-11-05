package com.example.TG_BOT.repositories;

import com.example.TG_BOT.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
