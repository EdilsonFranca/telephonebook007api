package com.telephonebook.repository;

import com.telephonebook.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, String> {
    Boolean existsByEmail(String email);

    User findByEmail(String name);

    Optional<User> findByName(String email);
}
