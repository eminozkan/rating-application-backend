package dev.ozkan.ratingapp.core.registration;

import dev.ozkan.ratingapp.support.result.CrudResult;


public interface RegistrationService {

    CrudResult register(RegistrationServiceRequest request);
}
