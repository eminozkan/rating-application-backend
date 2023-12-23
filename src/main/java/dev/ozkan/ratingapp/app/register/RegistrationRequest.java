package dev.ozkan.ratingapp.app.register;

import dev.ozkan.ratingapp.core.registration.RegistrationServiceRequest;

public record RegistrationRequest(
        String email,
        String username,
        String password,
        String name
) {
    RegistrationServiceRequest toServiceRequest(){
        return new RegistrationServiceRequest()
                .setEmail(email)
                .setUsername(username)
                .setPassword(password)
                .setName(name);
    }
}
