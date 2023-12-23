package dev.ozkan.ratingapp.app.register;

import dev.ozkan.ratingapp.business.core.ResponseMessage;
import dev.ozkan.ratingapp.business.core.resulthandler.BusinessResultHandler;
import dev.ozkan.ratingapp.core.registration.RegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class RegistrationController {
    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/register")
    ResponseEntity<ResponseMessage> register(@RequestBody RegistrationRequest request){
        var result = registrationService.register(request.toServiceRequest());
        return BusinessResultHandler.handleResult(result);
    }
}

