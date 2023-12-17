package dev.ozkan.ratingapp.core.registration;

import dev.ozkan.ratingapp.business.core.result.CreationResult;
import dev.ozkan.ratingapp.core.dto.User;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Validated
public interface RegistrationService {

    CreationResult<User> register(@Valid RegistrationServiceRequest request);
}
