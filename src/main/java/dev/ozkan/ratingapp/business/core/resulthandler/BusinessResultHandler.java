package dev.ozkan.ratingapp.business.core.resulthandler;

import dev.ozkan.ratingapp.business.core.ResponseMessage;
import dev.ozkan.ratingapp.business.core.result.CreationResult;
import dev.ozkan.ratingapp.business.core.result.OperationFailureReason;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BusinessResultHandler {
    public static ResponseEntity<ResponseMessage> handleResult(CreationResult<?> result){
        if (!result.isSuccess()){
            return handleFailureReason(result.getReason(), result.getMessage());
        }
        return new ResponseEntity<>(new ResponseMessage().setMessage(result.getMessage()),HttpStatus.OK);
    }

    private static ResponseEntity<ResponseMessage> handleFailureReason(OperationFailureReason reason, String message) {
        if (reason == OperationFailureReason.CONFLICT) {
            return new ResponseEntity<>(new ResponseMessage().setMessage(message), HttpStatus.CONFLICT);
        } else if (reason == OperationFailureReason.PRECONDITION_FAILED) {
            return new ResponseEntity<>(new ResponseMessage().setMessage(message), HttpStatus.PRECONDITION_FAILED);
        }
        return new ResponseEntity<>(new ResponseMessage().setMessage(message),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
