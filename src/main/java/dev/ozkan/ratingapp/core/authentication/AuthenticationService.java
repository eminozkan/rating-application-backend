package dev.ozkan.ratingapp.core.authentication;

import dev.ozkan.ratingapp.support.result.AuthenticationResult;

public interface AuthenticationService {
    AuthenticationResult authenticate(AuthenticationServiceRequest request);
}
