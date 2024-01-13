package dev.ozkan.ratingapp.core.registration;

import dev.ozkan.ratingapp.support.result.CreationResult;


public interface RegistrationService {

    CreationResult register(RegistrationServiceRequest request);
}
