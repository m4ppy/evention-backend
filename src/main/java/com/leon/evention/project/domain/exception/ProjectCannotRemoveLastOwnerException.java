package com.leon.evention.project.domain.exception;

public class ProjectCannotRemoveLastOwnerException extends RuntimeException {
    public ProjectCannotRemoveLastOwnerException(String message) {
        super(message);
    }

    public ProjectCannotRemoveLastOwnerException() { super("Project cannot remove final project owner."); }
}
