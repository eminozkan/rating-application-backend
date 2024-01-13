package dev.ozkan.ratingapp.core.authentication;

import dev.ozkan.ratingapp.support.result.CrudResult;

public interface AuthenticationService {
    CrudResult authenticate(AuthenticationServiceRequest request);
}
