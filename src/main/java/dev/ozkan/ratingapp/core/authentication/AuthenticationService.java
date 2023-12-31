package dev.ozkan.ratingapp.core.authentication;

import dev.ozkan.ratingapp.business.core.result.AuthenticationResult;

public interface AuthenticationService {
    AuthenticationResult authenticate(AuthenticationServiceRequest request);
}
