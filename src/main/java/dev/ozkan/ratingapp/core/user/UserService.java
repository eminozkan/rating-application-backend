package dev.ozkan.ratingapp.core.user;

import dev.ozkan.ratingapp.core.model.user.User;

import java.util.Optional;

public interface UserService {
    Optional<UserProfilePayload> getUserInformation(String userId);
}
