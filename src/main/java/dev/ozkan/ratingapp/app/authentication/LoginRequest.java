package dev.ozkan.ratingapp.app.authentication;

import dev.ozkan.ratingapp.core.authentication.AuthenticationServiceRequest;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record LoginRequest(
        @Email
        @NotNull
        @NotBlank
        String email,

        @NotNull
        @NotBlank
        @Length(min = 8)
        String password
) {

    AuthenticationServiceRequest toServiceRequest() {
        return new AuthenticationServiceRequest()
                .setEmail(email)
                .setPassword(password);
    }
}
