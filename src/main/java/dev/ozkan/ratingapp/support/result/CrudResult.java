package dev.ozkan.ratingapp.support.result;

public class CrudResult {
    private OperationResult  result;
    private OperationFailureReason reason;


    private String message;

    private CrudResult(){}

    public static CrudResult success(){
        return new CrudResult()
                .setResult(OperationResult.SUCCESS);
    }

    public static CrudResult success(String message){
        return new CrudResult()
                .setResult(OperationResult.SUCCESS)
                .setMessage(message);
    }

    public static CrudResult failure(OperationFailureReason reason){
        return new CrudResult()
                .setResult(OperationResult.FAILED)
                .setReason(reason);
    }

    public static CrudResult failure(OperationFailureReason reason, String message){
        return new CrudResult()
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
    private CrudResult setResult(OperationResult result) {
        this.result = result;
        return this;
    }

    public OperationFailureReason getReason() {
        return reason;
    }

    private CrudResult setReason(OperationFailureReason reason) {
        this.reason = reason;
        return this;
    }


    public String getMessage() {
        return message;
    }

    private CrudResult setMessage(String message) {
        this.message = message;
        return this;
    }
}
