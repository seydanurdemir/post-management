package com.youngadessi.demo.post.exception;

public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException(String message) {
        super(message + " data has invalid format!");
    }
}
