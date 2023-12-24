package dev.ozkan.ratingapp.core.authentication;

import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.Length;

public class AuthenticationServiceRequest {

    @Email
    private String email;

    @Length(min = 8)
    private String password;

    public String getEmail() {
        return email;
    }

    public AuthenticationServiceRequest setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public AuthenticationServiceRequest setPassword(String password) {
        this.password = password;
        return this;
    }
}
