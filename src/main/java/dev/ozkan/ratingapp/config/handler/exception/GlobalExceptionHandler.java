package dev.ozkan.ratingapp.config.handler.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<?> handleConstrainViolationException(MethodArgumentNotValidException ex){
        ErrorMessage message = new ErrorMessage();
        var exception = Objects.requireNonNull(ex.getDetailMessageArguments())[1].toString();
        message.addValidationErrorMessagesToList(exception);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", message.getMessages()));
    }





}

class ErrorMessage{
    List<String> messages;

    ErrorMessage(){
        messages = new ArrayList<>();
    }

    void add(String message){
        messages.add(message);
    }

    void addValidationErrorMessagesToList(String message){
        if (message.contains("password")){
            add("Password must be 8 characters long.");
        }

        if (message.contains("email")){
            add("Email must be in correct form.");
        }

        if (message.contains("name")){
            add("Name must not empty.");
        }
    }

    List<String> getMessages(){
        return messages;
    }
}