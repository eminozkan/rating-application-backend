package dev.ozkan.ratingapp.app.authentication;

import dev.ozkan.ratingapp.core.authentication.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping
@Validated
public class AuthenticationController {
    private final AuthenticationService service;

    public AuthenticationController(AuthenticationService service) {
        this.service = service;
    }

    @PutMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest request){
        var result = service.authenticate(request.toServiceRequest());
        if (result.isSuccess()){
            return ResponseEntity.ok(Map.of("token",result.getMessage()));
        }else{
            return new ResponseEntity<>(Map.of("message","Invalid credentials"), HttpStatus.UNAUTHORIZED);
    }
}

}

