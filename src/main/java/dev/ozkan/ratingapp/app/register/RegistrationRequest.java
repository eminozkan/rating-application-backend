package dev.ozkan.ratingapp.app.register;

import dev.ozkan.ratingapp.core.registration.RegistrationServiceRequest;

public record RegistrationRequest(
        String email,
        String password,
        String name
) {
    RegistrationServiceRequest toServiceRequest(){
        return new RegistrationServiceRequest()
                .setEmail(email)
                .setPassword(password)
                .setName(name);
    }
}
