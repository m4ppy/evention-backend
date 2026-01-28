package com.leon.evention.ticket.domain.exception;

public class DuplicateProjectMemberException extends RuntimeException {
    public DuplicateProjectMemberException(String message) {
        super(message);
    }
    public DuplicateProjectMemberException() { super("This member is already in the project!"); }
}
