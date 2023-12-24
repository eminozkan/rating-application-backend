package dev.ozkan.ratingapp.config.auth;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.auth")
public class AuthenticationProperties {
    private String cookieName = "SESSION";
    private String cookiePath = "/api";

    private String cookieSameSite = "Lax";

    public String getCookieName() {
        return cookieName;
    }

    public AuthenticationProperties setCookieName(String cookieName) {
        this.cookieName = cookieName;
        return this;
    }

    public String getCookiePath() {
        return cookiePath;
    }

    public AuthenticationProperties setCookiePath(String cookiePath) {
        this.cookiePath = cookiePath;
        return this;
    }

    public String getCookieSameSite() {
        return cookieSameSite;
    }

    public AuthenticationProperties setCookieSameSite(String cookieSameSite) {
        this.cookieSameSite = cookieSameSite;
        return this;
    }
}