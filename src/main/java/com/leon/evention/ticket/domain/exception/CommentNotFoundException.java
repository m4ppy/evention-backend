package com.leon.evention.ticket.domain.exception;

public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException(String message) {
        super(message);
    }
    public CommentNotFoundException() { super("comment to find is not exist in ticket."); }
}
