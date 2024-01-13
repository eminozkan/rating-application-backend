package dev.ozkan.ratingapp.support.result;

public class CreationResult{
    private OperationResult  result;
    private OperationFailureReason reason;


    private String message;

    private CreationResult(){}

    public static CreationResult success(){
        return new CreationResult()
                .setResult(OperationResult.SUCCESS);
    }

    public static CreationResult success(String message){
        return new CreationResult()
                .setResult(OperationResult.SUCCESS)
                .setMessage(message);
    }

    public static  CreationResult failure(OperationFailureReason reason){
        return new CreationResult()
                .setResult(OperationResult.FAILED)
                .setReason(reason);
    }

    public static CreationResult failure(OperationFailureReason reason,String message){
        return new CreationResult()
                .setResult(OperationResult.FAILED)
                .setReason(reason)
                .setMessage(message);
    }

    public OperationResult getResult() {
        return result;
    }

    public boolean isSuccess(){
        return result.equals(OperationResult.SUCCESS);
    }
    private CreationResult setResult(OperationResult result) {
        this.result = result;
        return this;
    }

    public OperationFailureReason getReason() {
        return reason;
    }

    private CreationResult setReason(OperationFailureReason reason) {
        this.reason = reason;
        return this;
    }


    public String getMessage() {
        return message;
    }

    private CreationResult setMessage(String message) {
        this.message = message;
        return this;
    }
}
