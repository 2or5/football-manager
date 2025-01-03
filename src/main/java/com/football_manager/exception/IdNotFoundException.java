package com.football_manager.exception;

public class IdNotFoundException extends RuntimeException{

    public IdNotFoundException(String message) {
        super(message);
    }
}
