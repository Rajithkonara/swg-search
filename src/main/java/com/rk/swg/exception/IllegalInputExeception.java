package com.rk.swg.exception;

public class IllegalInputExeception extends Exception {

    public IllegalInputExeception() {
        super("Invalid input provided ");
    }
    public IllegalInputExeception(String message) {
        super(message);
    }
    public IllegalInputExeception(String message, Throwable cause) {
        super(message, cause);
    }

}
