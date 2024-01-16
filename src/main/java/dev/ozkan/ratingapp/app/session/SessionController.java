package dev.ozkan.ratingapp.app.session;

import dev.ozkan.ratingapp.config.UserSession;
import dev.ozkan.ratingapp.core.model.user.UserRole;
import dev.ozkan.ratingapp.core.user.UserService;
import dev.ozkan.ratingapp.support.response.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/me")
public class SessionController {

    private final UserService userService;

    public SessionController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/session")
    ResponseEntity<?> getSession(UserSession userSession) {
        return new ResponseEntity<>(new SessionPayload(userSession.id(), userSession.username(),userSession.role()), HttpStatus.OK);
    }

    @GetMapping
    ResponseEntity<?> getUserInformation(UserSession session){
        var result = userService.getUserInformation(session.id());
        if (result.isEmpty()){
            return ResponseEntity
                    .status(404)
                    .body(new ResponseMessage().setMessage("user not found"));
        }
        return ResponseEntity.ok(result.get());
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
