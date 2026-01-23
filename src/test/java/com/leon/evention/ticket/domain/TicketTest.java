package com.leon.evention.ticket.domain;

import com.leon.evention.member.domain.Member;
import com.leon.evention.project.domain.Project;
import com.leon.evention.ticket.domain.exception.UnauthorizedTicketOperationException;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TicketTest {
    @Test
    void non_maintainer_cannot_change_ticket_status() {
        Member owner = new Member(UUID.randomUUID());
        Member contributor = new Member(UUID.randomUUID());

        Project project = new Project(owner);

        project.addContributor(contributor);

        Ticket ticket = Ticket.open(project);

        assertThrows(
                UnauthorizedTicketOperationException.class,
                () -> ticket.changeStatus(TicketStatus.IN_PROGRESS, contributor)
        );
    }

}
