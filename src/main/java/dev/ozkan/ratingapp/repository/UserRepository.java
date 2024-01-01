package dev.ozkan.ratingapp.repository;

import dev.ozkan.ratingapp.core.model.user.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> save(User user);

    Optional<User> getByEmail(String email);
}
