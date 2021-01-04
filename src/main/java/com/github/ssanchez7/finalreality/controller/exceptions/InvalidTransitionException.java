package com.github.ssanchez7.finalreality.controller.exceptions;

/**
 * exception for a failed transition to a phase
 */
public class InvalidTransitionException extends Exception {

    public InvalidTransitionException(String message){
        super(message);
    }
}
