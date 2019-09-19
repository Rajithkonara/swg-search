package com.rk.swg.exception;

public class IllegalInputException extends Exception {

    public IllegalInputException() {
        super("Invalid input provided ");
    }
    public IllegalInputException(String message) {
        super(message);
    }
    public IllegalInputException(String message, Throwable cause) {
        super(message, cause);
    }

}
