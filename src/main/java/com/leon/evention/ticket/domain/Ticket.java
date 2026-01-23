package com.leon.evention.ticket.domain;

import com.leon.evention.project.domain.Project;
import com.leon.evention.member.domain.Member;
import com.leon.evention.ticket.domain.exception.UnauthorizedTicketOperationException;

public class Ticket {

    private TicketStatus status;
    private final Project project;

    public Ticket(Project project) {
        this.project = project;
    }

    public static Ticket open(Project project) {
        return new Ticket(project);
    }

    public void changeStatus(TicketStatus newStatus, Member actor) throws UnauthorizedTicketOperationException {
        if (!project.isMaintainer(actor)) {
            throw new UnauthorizedTicketOperationException();
        }
        this.status = newStatus;
    }
}
