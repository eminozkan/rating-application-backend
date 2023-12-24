package dev.ozkan.ratingapp.app.authentication;

import dev.ozkan.ratingapp.core.authentication.AuthenticationServiceRequest;
import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.Length;

public record LoginRequest(
        @Email
        String email,
        @Length(min = 8)
        String password
) {

    AuthenticationServiceRequest toServiceRequest(){
        return new AuthenticationServiceRequest()
                .setEmail(email)
                .setPassword(password);
    }
}
