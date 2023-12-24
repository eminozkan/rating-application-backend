package dev.ozkan.ratingapp.app.authentication;

import dev.ozkan.ratingapp.config.auth.AuthenticationProperties;
import dev.ozkan.ratingapp.core.authentication.AuthenticationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping
public class AuthenticationController {
    private final AuthenticationService service;
    private final AuthenticationProperties properties;

    public AuthenticationController(AuthenticationService service, AuthenticationProperties properties) {
        this.service = service;
        this.properties = properties;
    }

    @PutMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpServletResponse response){
        var result = service.authenticate(request.toServiceRequest());
        if (result.isSuccess()){
            final Cookie deleteCookie = new Cookie(properties.getCookieName(), "");
            deleteCookie.setPath(properties.getCookiePath());
            deleteCookie.setHttpOnly(true);
            deleteCookie.setSecure(false);
            deleteCookie.setMaxAge(0);
            response.addCookie(deleteCookie);

            String token = result.getMessage();

            Cookie cookie = new Cookie(properties.getCookieName(), token);
            cookie.setPath(properties.getCookiePath());
            cookie.setHttpOnly(true);
            cookie.setSecure(false);

            new SameSiteCookie(cookie, properties.getCookieSameSite()).
                    writeTo(response);



            return ResponseEntity.ok(Map.of("email",request.email()));
        }else{
            return new ResponseEntity<>(Map.of("message","Invalid credentials"), HttpStatus.UNAUTHORIZED);
    }
}

public static class SameSiteCookie {

    private final String name;
    private final String domain;

    private final String value;
    private final String path;
    private final int maxAge;
    private final boolean secure;
    private final boolean httpOnly;
    private final String sameSite;

    public SameSiteCookie(Cookie cookie, String sameSite) {
        this.name = cookie.getName();
        this.value = cookie.getValue();
        this.domain = cookie.getDomain();
        this.path = cookie.getPath();
        this.maxAge = cookie.getMaxAge();
        this.secure = cookie.getSecure();
        this.httpOnly = cookie.isHttpOnly();
        this.sameSite = sameSite;
    }

    public String getHeaderName() {
        return "Set-Cookie";
    }

    public String getHeaderValue() {
        String value = String.format("%s=%s", name, this.value);
        if (isNotEmpty(domain)) {
            value += String.format("; Domain=%s", domain);
        }
        if (maxAge != -1) {
            value += String.format("; Max-Age=%d", maxAge);
        }
        if (isNotEmpty(path)) {
            value += String.format("; Path=%s", path);
        }
        if (secure) {
            value += "; Secure";
        }
        if (httpOnly) {
            value += "; HttpOnly";
        }
        if (isNotEmpty(sameSite)) {
            value += String.format("; SameSite=%s", sameSite);
        }

        return value;
    }

    public void writeTo(HttpServletResponse response) {
        response.addHeader(getHeaderName(), getHeaderValue());
    }

    private boolean isNotEmpty(String param) {
        return !ObjectUtils.isEmpty(param);
    }

    public String getName() {
        return name;
    }

    public String getDomain() {
        return domain;
    }

    public String getPath() {
        return path;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public boolean isSecure() {
        return secure;
    }

    public boolean isHttpOnly() {
        return httpOnly;
    }

    public String getSameSite() {
        return sameSite;
    }

}
}

