package com.realestate.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT) // 409 Conflict
public class UserConflictException extends RuntimeException {
    public UserConflictException(String message) {
        super(message);
    }
}