package com.leon.evention.ticket.domain;

import com.leon.evention.project.domain.Project;
import com.leon.evention.project.domain.ProjectMember;
import com.leon.evention.ticket.domain.exception.UnauthorizedTicketOperationException;

public class Ticket {

    private TicketStatus status;
    private Project project;

    public Ticket(Project project) {
        this.project = project;
    }

    static Ticket open(Project project) {
        return new Ticket(project);
    }

    void changeStatus(TicketStatus newStatus, ProjectMember actor) throws UnauthorizedTicketOperationException {
        if (!project.isMaintainer(actor)) {
            throw new UnauthorizedTicketOperationException();
        }
        this.status = newStatus;
    }
}
