package dev.ozkan.ratingapp.app.session;

import dev.ozkan.ratingapp.config.UserSession;
import dev.ozkan.ratingapp.core.model.user.User;
import dev.ozkan.ratingapp.core.model.user.UserRole;
import dev.ozkan.ratingapp.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/me")
public class SessionController {

    private final UserRepository userRepository;

    public SessionController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    ResponseEntity<?> getSession(UserSession userSession) {
        return new ResponseEntity<>(new SessionPayload(userSession.id(), userSession.username(),userSession.role()), HttpStatus.OK);
    }


    public record SessionPayload(
            String id,
            String email,
            String role
    ) {
        SessionPayload(String id, String email, UserRole role) {
            this(
                    id,
                    email,
                    role.name()
            );
        }
    }
}
