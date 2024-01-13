package dev.ozkan.ratingapp.support.result;

public class AuthenticationResult{

    private Boolean isSuccess;
    private String message;
    private OperationFailureReason reason;

    private AuthenticationResult(){}

    public static AuthenticationResult success(){
        return new AuthenticationResult()
                .setSuccess(true);
    }

    public static AuthenticationResult success(String message){
        return new AuthenticationResult()
                .setSuccess(true)
                .setMessage(message);
    }
    public static AuthenticationResult failed(OperationFailureReason reason){
        return new AuthenticationResult()
                .setReason(reason)
                .setSuccess(false);
    }

    public static AuthenticationResult failed(OperationFailureReason reason, String message){
        return new AuthenticationResult()
                .setMessage(message)
                .setSuccess(false)
                .setReason(reason);
    }

    public Boolean isSuccess() {
        return isSuccess;
    }

    public AuthenticationResult setSuccess(Boolean success) {
        isSuccess = success;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public AuthenticationResult setMessage(String message) {
        this.message = message;
        return this;
    }

    public OperationFailureReason getReason() {
        return reason;
    }

    public AuthenticationResult setReason(OperationFailureReason reason) {
        this.reason = reason;
        return this;
    }
}