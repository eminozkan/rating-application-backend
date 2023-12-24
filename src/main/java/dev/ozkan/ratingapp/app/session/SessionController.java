package dev.ozkan.ratingapp.app.session;

import dev.ozkan.ratingapp.config.UserSession;
import dev.ozkan.ratingapp.core.dto.User;
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
        final User user = userRepository.getByEmail(userSession.username()).orElseThrow();
        return new ResponseEntity<>(new SessionPayload(user.getUserId(), user.getEmail()), HttpStatus.OK);
    }


    public record SessionPayload(
            String id,
            String email
    ) {
        SessionPayload(User user) {
            this(
                    user.getUserId(),
                    user.getEmail()
            );
        }
    }
}
