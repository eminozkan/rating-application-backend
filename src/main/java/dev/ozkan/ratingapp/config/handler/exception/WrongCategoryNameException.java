package dev.ozkan.ratingapp.config.handler.exception;

public class WrongCategoryNameException extends Exception{
    public WrongCategoryNameException(String message) {
        super(message);
    }
}
