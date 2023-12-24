package dev.ozkan.ratingapp.core.user;

import org.springframework.security.crypto.password.PasswordEncoder;

public interface UserPasswordEncoder {
    String encodePassword(String password);

    PasswordEncoder getEncoder();

    boolean matches(String password, String passwordHash);
}
