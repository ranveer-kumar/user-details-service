package com.user.exception;

public class UserNotFoundException extends Exception {
    /**
     * Constructs a new instance of UserNotFoundException with the given message
     * @param message
     */
    public UserNotFoundException(String message){
        super(message);
    }

}
