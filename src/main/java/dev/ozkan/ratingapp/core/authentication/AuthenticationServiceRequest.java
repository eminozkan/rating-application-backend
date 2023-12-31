package dev.ozkan.ratingapp.core.authentication;


public class AuthenticationServiceRequest {

    private String email;

    private String password;

    public String getEmail() {
        return email;
    }

    public AuthenticationServiceRequest setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public AuthenticationServiceRequest setPassword(String password) {
        this.password = password;
        return this;
    }
}
