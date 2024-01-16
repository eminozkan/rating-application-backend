package dev.ozkan.ratingapp.core.user;

public class UserProfilePayload {
    private String maskedEmail;
    private String name;

    private String userType;

    public String getMaskedEmail() {
        return maskedEmail;
    }

    public UserProfilePayload setMaskedEmail(String maskedEmail) {
        this.maskedEmail = maskedEmail;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserProfilePayload setName(String name) {
        this.name = name;
        return this;
    }

    public String getUserType() {
        return userType;
    }

    public UserProfilePayload setUserType(String userType) {
        this.userType = userType;
        return this;
    }
}
