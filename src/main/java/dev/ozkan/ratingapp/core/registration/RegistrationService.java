package dev.ozkan.ratingapp.core.registration;

import dev.ozkan.ratingapp.business.core.result.CreationResult;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;


@Validated
public interface RegistrationService {

    CreationResult register(@Valid RegistrationServiceRequest request);
}
