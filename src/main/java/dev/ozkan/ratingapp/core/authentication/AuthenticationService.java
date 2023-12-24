package dev.ozkan.ratingapp.core.authentication;

import dev.ozkan.ratingapp.business.core.result.AuthenticationResult;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

@Validated
public interface AuthenticationService {
    AuthenticationResult authenticate(@Valid AuthenticationServiceRequest request);
}
