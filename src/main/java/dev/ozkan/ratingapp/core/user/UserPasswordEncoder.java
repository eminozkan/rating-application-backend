package dev.ozkan.ratingapp.core.user;

public interface UserPasswordEncoder {
    String encodePassword(String password);
}
