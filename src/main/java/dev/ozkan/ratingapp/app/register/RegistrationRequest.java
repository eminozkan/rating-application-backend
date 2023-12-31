package dev.ozkan.ratingapp.app.register;

import dev.ozkan.ratingapp.core.registration.RegistrationServiceRequest;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record RegistrationRequest(
        @Email
        @NotBlank
        @NotNull
        String email,
        @Length(min = 8)
        @NotNull
        String password,
        @NotBlank
        @NotNull
        String name
) {
    RegistrationServiceRequest toServiceRequest() {
        return new RegistrationServiceRequest()
                .setEmail(email)
                .setPassword(password)
                .setName(name);
    }
}
