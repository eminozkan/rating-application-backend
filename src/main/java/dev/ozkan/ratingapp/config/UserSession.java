package dev.ozkan.ratingapp.config;

import dev.ozkan.ratingapp.core.model.user.UserRole;

public record UserSession(String id, String username, UserRole role){
}
