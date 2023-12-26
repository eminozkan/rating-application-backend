package dev.ozkan.ratingapp.app.register;

import dev.ozkan.ratingapp.business.core.resulthandler.BusinessResultHandler;
import dev.ozkan.ratingapp.core.registration.RegistrationService;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/auth/register")
    ResponseEntity<?> register(@RequestBody RegistrationRequest request){
        var result = registrationService.register(request.toServiceRequest());
        if (!result.isSuccess()){
            BusinessResultHandler.handleFailureReason(result.getReason(),result.getMessage());
        }
        RegistrationResponse response = new RegistrationResponse(result.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
