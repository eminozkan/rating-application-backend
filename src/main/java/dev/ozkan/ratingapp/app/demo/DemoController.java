package dev.ozkan.ratingapp.app.demo;

import dev.ozkan.ratingapp.config.UserSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/demo/hello")
    public ResponseEntity<?> sayHello(UserSession session){
        return ResponseEntity.ok(session.id() + session.username());
    }
}
