package com.user.exception;

public class OperationErrorException extends Exception {
    /**
     * Constructs a new instance of UserNotFoundException with the given message
     * @param message
     */
    public OperationErrorException(String message){
        super(message);
    }

}
