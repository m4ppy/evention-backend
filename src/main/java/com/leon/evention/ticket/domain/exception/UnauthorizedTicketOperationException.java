package com.leon.evention.ticket.domain.exception;

public class UnauthorizedTicketOperationException extends RuntimeException {

    public UnauthorizedTicketOperationException(String message) {
        super(message);
    }

    public UnauthorizedTicketOperationException() {
        super("Actor is not allowed to perform this ticket operation");
    }
}
