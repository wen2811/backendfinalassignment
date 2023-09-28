package com.novi.backendfinalassignment.exceptions;

public class TimeslotNotAvailableException extends RuntimeException{
    public TimeslotNotAvailableException() {
    }
    public TimeslotNotAvailableException(String message) {
        super(message);
    }
}
