package dev.ozkan.ratingapp.business.core;

public class ResponseMessage{
    private String message;

    public String getMessage() {
        return message;
    }

    public ResponseMessage setMessage(String message) {
        this.message = message;
        return this;
    }
}
