package com.leon.evention.project.domain.exception;

public class UnauthorizedProjectOperationException extends RuntimeException {
    public UnauthorizedProjectOperationException(String message) {
        super(message);
    }

    public UnauthorizedProjectOperationException() {
        super("Actor is not allowed to perform this project operation");
    }
}
