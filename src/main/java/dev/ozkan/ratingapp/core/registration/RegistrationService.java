package dev.ozkan.ratingapp.core.registration;

import dev.ozkan.ratingapp.business.core.result.CreationResult;


public interface RegistrationService {

    CreationResult register(RegistrationServiceRequest request);
}
