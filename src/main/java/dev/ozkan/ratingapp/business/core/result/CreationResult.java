package dev.ozkan.ratingapp.business.core.result;

public class CreationResult <T>{
    private OperationResult  result;
    private OperationFailureReason reason;

    private T entity;

    private String message;

    private CreationResult(){}

    public static <T> CreationResult<T> success(T entity){
        return new CreationResult<T>()
                .setResult(OperationResult.SUCCESS)
                .setEntity(entity);
    }

    public static <T> CreationResult<T> success(T entity,String message){
        return new CreationResult<T>()
                .setResult(OperationResult.SUCCESS)
                .setEntity(entity)
                .setMessage(message);
    }

    public static <T> CreationResult<T> failure(OperationFailureReason reason){
        return new CreationResult<T>()
                .setResult(OperationResult.FAILED)
                .setReason(reason);
    }

    public static <T> CreationResult<T> failure(OperationFailureReason reason,String message){
        return new CreationResult<T>()
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
    private CreationResult<T> setResult(OperationResult result) {
        this.result = result;
        return this;
    }

    public OperationFailureReason getReason() {
        return reason;
    }

    private CreationResult<T> setReason(OperationFailureReason reason) {
        this.reason = reason;
        return this;
    }

    public T getEntity() {
        return entity;
    }

    private CreationResult<T> setEntity(T entity) {
        this.entity = entity;
        return this;
    }

    public String getMessage() {
        return message;
    }

    private CreationResult<T> setMessage(String message) {
        this.message = message;
        return this;
    }
}
