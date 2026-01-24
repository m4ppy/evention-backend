package com.leon.evention.ticket.domain;

import com.leon.evention.project.domain.Project;
import com.leon.evention.member.domain.Member;
import com.leon.evention.ticket.domain.exception.UnauthorizedTicketOperationException;

public class Ticket {

    private final Project project;
    private TicketStatus status;

    public Ticket(Project project) {
        this.project = project;
        this.status = TicketStatus.OPEN;
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

    public TicketStatus getStatus() {
        return this.status;
    }
}
