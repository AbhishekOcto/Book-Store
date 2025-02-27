package com.bookStore.exception;

public class MyBookNotFoundException extends RuntimeException{
    public MyBookNotFoundException(String message) {
        super(message);
    }
    
}
