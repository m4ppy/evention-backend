package com.leon.evention.project.domain.exception;

public class ProjectMemberNotFoundException extends RuntimeException {
    public ProjectMemberNotFoundException(String message) {
        super(message);
    }

    public ProjectMemberNotFoundException() { super("Member to remove is not exist in this project."); }
}
