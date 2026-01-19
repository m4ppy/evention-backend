package com.leon.evention.ticket.domain;

import com.leon.evention.project.domain.Project;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TicketTest {
    @Test
    void non_maintainer_cannot_change_ticket_status() {
        Project project = new Project();
        Member contributor = new Member(Role.CONTRIBUTOR);

        Ticket ticket = Ticket.open(project);

        assertThrows(
                UnauthorizedOperationException.class,
                () -> ticket.changeStatus(TicketStatus.IN_PROGRESS, contributor)
        );
    }

}
