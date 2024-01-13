package dev.ozkan.ratingapp.support.result;

public class UpdateResult {
    private Boolean isSuccess;
    private String message;
    private OperationFailureReason reason;

    private UpdateResult(){}

    public static UpdateResult success(){
        return new UpdateResult()
                .setSuccess(true);
    }

    public static UpdateResult success(String message){
        return new UpdateResult()
                .setSuccess(true)
                .setMessage(message);
    }
    public static UpdateResult failed(OperationFailureReason reason){
        return new UpdateResult()
                .setReason(reason)
                .setSuccess(false);
    }

    public static UpdateResult failed(OperationFailureReason reason, String message){
        return new UpdateResult()
                .setMessage(message)
                .setSuccess(false)
                .setReason(reason);
    }

    public Boolean isSuccess() {
        return isSuccess;
    }

    public UpdateResult setSuccess(Boolean success) {
        isSuccess = success;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public UpdateResult setMessage(String message) {
        this.message = message;
        return this;
    }

    public OperationFailureReason getReason() {
        return reason;
    }

    public UpdateResult setReason(OperationFailureReason reason) {
        this.reason = reason;
        return this;
    }
}
